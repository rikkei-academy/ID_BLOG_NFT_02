package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.jwt.JwtTokenProvider;
import ra.model.entity.ERole;
import ra.model.entity.Roles;
import ra.model.entity.Users;
import ra.model.repository.UserRepository;
import ra.model.service.RoleService;
import ra.model.service.UserService;
import ra.payload.request.ChangePassword;
import ra.payload.request.LoginRequest;
import ra.payload.request.SignupRequest;
import ra.payload.request.UpdaUserQuyen;
import ra.payload.response.JwtResponse;
import ra.payload.response.ListUserResponse;
import ra.payload.response.MessageResponse;
import ra.security.CustomUserDetail;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Usermame is already"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already"));
        }
        Users user = new Users();
        user.setUserName(signupRequest.getUserName());
        user.setPasswords(encoder.encode(signupRequest.getPasswords()));
        user.setEmail(signupRequest.getEmail());
        user.setPhone(signupRequest.getPhone());
        user.setUserStatus(true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateNow = new Date();
        String strNow = sdf.format(dateNow);
        try {
            user.setCreated(sdf.parse(strNow));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Set<String> strRoles = signupRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles==null){
            //User quyen mac dinh
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(()->new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role->{
                switch (role){
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "moderator":
                        Roles modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(modRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPasswords())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        //Sinh JWT tra ve client
        String jwt = tokenProvider.generateToken(customUserDetail);
        //Lay cac quyen cua user
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(item->item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt,customUserDetail.getUsername(),customUserDetail.getEmail(),
                customUserDetail.getPhone(),listRoles));
    }
    @GetMapping("/logOut")
    public ResponseEntity<?> logOut(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        // Clear the authentication from server-side (in this case, Spring Security)
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("You have been logged out.");
    }

    @PutMapping("/changePass")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePass) {
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users users = userService.findByUserId(userDetails.getUserId());
        BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
        boolean passChecker = bc.matches(changePass.getOldPassword(), users.getPasswords());
        if (passChecker) {
            boolean checkDuplicate = bc.matches(changePass.getPassword(), users.getPasswords());
            if (checkDuplicate) {
                return ResponseEntity.ok(new MessageResponse("Mật khẩu mới phải khác mật khẩu cũ!"));
            } else {
                users.setPasswords(encoder.encode(changePass.getPassword()));
                userService.saveOrUpdate(users);
                return ResponseEntity.ok(new MessageResponse("Đổi mật khẩu thành công !"));
            }
        } else {
            return ResponseEntity.ok(new MessageResponse("Mật khẩu không hợp lệ ! Đổi mật khẩu thất bại"));
        }
    }



    ///////////////////searchId/////////////////////////////
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Users getUserById(@PathVariable("userId") int userId){
        return userService.findByUserId(userId);
    }

    ///////////////////////end searchId/////////////////////




    /////////////////////getAllUser///////////////////////////
    @GetMapping("/getAllUser")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")

    public List<ListUserResponse> getAllProduct() {
        List<ListUserResponse> listUserResponses = new ArrayList<>();
        List<Users> listUser = userService.findAll();
        for (Users use : listUser) {
            ListUserResponse users = new ListUserResponse();
            users.setUserId(use.getUserId());
            users.setUserName(use.getUserName());
            users.setCreated(use.getCreated());
            users.setEmail(use.getEmail());
            users.setUserAvatar(use.getUserAvatar());
            users.setPhone(use.getPhone());
            users.setUserStatus(use.isUserStatus());
            listUserResponses.add(users);
        }
        return listUserResponses;
    }


    /////////////////////end getAllUser///////////////////



    /////////////////////////search UserName//////////////////////////
    @GetMapping("/searchUser")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Users> searchUsersss(@RequestParam("userName") String userName){
        return userService.searchUserName(userName);
    }
    //////////////////////////end search UserName////////////////////////



    /////////////////////////////phan quyen User////////////////////////////////
    @PostMapping("/phanquyen/{userId}")
    public Users updateUser(@PathVariable("userId") int userId, @RequestBody UpdaUserQuyen updaUserQuyen){
        Users user = userService.findByUserId(userId);
        Set<String> strRoles = updaUserQuyen.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if(strRoles == null){
            Roles userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                    case "moderator":
                        Roles modRole = roleService.findByRoleName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(modRole);
                    case "user":
                        Roles userRole = roleService.findByRoleName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        return userService.saveOrUpdate(user);
    }
    /////////////////////////////end phan quyen User////////////////////////////////

////////////////////////sap xep user/////////////////////////////////////////////
    @GetMapping("/sortByName")
    public ResponseEntity<List<Users>> sortByUserName(@RequestParam("direction") String direction){
        List<Users> listUser = userService.sortByStudentName(direction);
        return new ResponseEntity<>(listUser, HttpStatus.OK);
    }
    ///////////////////////end sapxep user////////////////////////


    ////////////////////////phan trang user/////////////////////////////

    @GetMapping("/getPagging")
    public ResponseEntity<Map<String,Object>> getPagging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ){
        Pageable pageable = PageRequest.of(page,size);
        Page<Users> pageUser = userService.pagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("user",pageUser.getContent());
        data.put("total",pageUser.getSize());
        data.put("totalItems",pageUser.getTotalElements());
        data.put("totalPages",pageUser.getTotalPages());
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    ///////////////////////end phan trang/////////////////////////////

    @GetMapping("/getPaggingAndSortByName")
    public ResponseEntity<Map<String,Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam String direction){
        Sort.Order order;
        if(direction.equals("asc")){
            order = new Sort.Order(Sort.Direction.ASC,"userName");
        }else {
            order = new Sort.Order(Sort.Direction.DESC,"userName");
        }
        Pageable pageable = PageRequest.of(page, size,Sort.by(order));
        Page<Users> pageUser = userService.pagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("user",pageUser.getContent());
        data.put("total",pageUser.getSize());
        data.put("totalItems",pageUser.getTotalElements());
        data.put("totalPages",pageUser.getTotalPages());
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
    /////////////////////////////start phan_trang$tim_kiem///////////////////////

@PutMapping("/blockUser/{userId}")
@PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> blockUser(@PathVariable("userId") int userId){
        try {
            Users users = userService.findByUserId(userId);
            users.setUserStatus(false);
            userService.saveOrUpdate(users);
            return ResponseEntity.ok("yes sir bạn đã khóa thành công");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("bạn chưa khóa thành công");
        }
    }

    /////////////////////////UnlockUser//////////////////////////////////////
    @PutMapping("/unlockUser/{userId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> unlockUser(@PathVariable("userId") int userId){
        try {
            Users users = userService.findByUserId(userId);
            users.setUserStatus(true);
            userService.saveOrUpdate(users);
            return ResponseEntity.ok("Chúc mừng bạn đã mở khoa thành công thành công");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("Bạn chưa cập nhật thành công");
        }
    }

    @GetMapping("filter/{option}")
    public List<Users> listFilter(@PathVariable("option") Integer option){
        return userService.listFilter(option);
    }




}
