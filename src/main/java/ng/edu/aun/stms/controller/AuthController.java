package ng.edu.aun.stms.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ng.edu.aun.stms.model.Role;
import ng.edu.aun.stms.model.User;
import ng.edu.aun.stms.service.RoleService;
import ng.edu.aun.stms.service.TwilioService;
import ng.edu.aun.stms.service.UserService;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @SuppressWarnings("unused")
    @Autowired
    private TwilioService twilioService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/landing")
    public String landingPage() {
        return "landing";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @PostMapping("/register")
    public String addNew(User user, RedirectAttributes redir, HttpSession session,
            @RequestParam(required = false) boolean isFaculty) {
        if (userService.findByUsername(user.getUsername()) != null) {
            redir.addFlashAttribute("error", "Username is already taken.");
            return "redirect:/register";
        }

        if (user.getPassword().length() < 7) {
            redir.addFlashAttribute("error", "Password must be at least 7 characters long.");
            return "redirect:/register";
        }

        Set<Role> roles = new HashSet<>();

        if (isFaculty) {
            roles.add(roleService.findByRoleName("FACULTY"));
        } else {
            roles.add(roleService.findByRoleName("STUDENT"));
        }

        String otp = String.valueOf((int) ((Math.random() * 9000) + 1000));
        user.setOtp(otp);
        user.setOtpExpiryTime(System.currentTimeMillis() + (5 * 60 * 1000));
        user.setVerified(false); // OTP valid for 5 minutes

        user.setRoles(roles);
        userService.save(user);

        session.setAttribute("username", user.getUsername());
        session.setAttribute("phonenumber", user.getPhone_number());

        twilioService.sendSms(user.getPhone_number(), "Your verification OTP is: " +
                otp);

        redir.addFlashAttribute("message", "Registration successful! Please verify your account.");
        return "redirect:/verify";
    }

    @GetMapping("/verify")
    public String verifyPage(Model model, @SessionAttribute("username") String username,
            @SessionAttribute("phonenumber") String phonenumber) {
        model.addAttribute("username", username);
        model.addAttribute("phonenumber", phonenumber);
        return "verify";
    }

    @PostMapping("/verify")
    public String verifyOtp(String otp, RedirectAttributes redir, @SessionAttribute("username") String username) {
        User user = userService.findByUsername(username);

        if (user == null) {
            redir.addFlashAttribute("error", "User not found.");
            return "redirect:/verify";
        }

        if (user.getOtpExpiryTime() < System.currentTimeMillis()) {
            redir.addFlashAttribute("error", "OTP has expired. A new OTP has been sent to your phone.");
            String newOtp = String.valueOf((int) ((Math.random() * 9000) + 1000));
            user.setOtp(newOtp);
            user.setOtpExpiryTime(System.currentTimeMillis() + (5 * 60 * 1000));
            userService.update(user);
            twilioService.sendSms(user.getPhone_number(), "Your new OTP is: " + newOtp);
            return "redirect:/verify";
        }

        if (!user.getOtp().equals(otp)) {
            redir.addFlashAttribute("error", "Invalid OTP. Please try again.");
            return "redirect:/verify";
        }

        user.setOtp(null);
        user.setOtpExpiryTime(null);
        user.setVerified(true);
        userService.update(user);

        redir.addFlashAttribute("message", "Account verified! You can now login.");
        return "redirect:/login";
    }

    @PostMapping("/resend-otp")
    public String resendOtp(RedirectAttributes redir, @SessionAttribute("username") String username) {
        User user = userService.findByUsername(username);

        if (user == null) {
            redir.addFlashAttribute("error", "User not found.");
            return "redirect:/verify";
        }

        if (user.getOtpExpiryTime() < System.currentTimeMillis()) {
            redir.addFlashAttribute("error", "OTP has expired. A new OTP has been sent to your phone.");
            String newOtp = String.valueOf((int) ((Math.random() * 9000) + 1000));
            user.setOtp(newOtp);
            user.setOtpExpiryTime(System.currentTimeMillis() + (5 * 60 * 1000));
            userService.update(user);
            // twilioService.sendSms(user.getPhone_number(), "Your new OTP is: " + newOtp);
            return "redirect:/verify";
        }

        // twilioService.sendSms(user.getPhone_number(), "Your OTP is: " +
        // user.getOtp());
        redir.addFlashAttribute("message", "OTP has been resent.");
        return "redirect:/verify";
    }
}
