package main.java.com.project.controller;

import main.java.com.project.dto.CreditHistory;
import main.java.com.project.dto.Member;
import main.java.com.project.exception.EmailDuplicateException;
import main.java.com.project.exception.InsufficientBalanceException;
import main.java.com.project.exception.MemberNotFoundException;
import main.java.com.project.service.MemberService;
import main.java.com.project.service.MemberServiceImpl;
import main.java.com.project.view.FailView;
import main.java.com.project.view.SuccessView;

import java.sql.SQLException;
import java.util.List;

public class MemberController {
    private static final MemberController instance = new MemberController();
    private final MemberService memberService = MemberServiceImpl.getInstance();
    private MemberController() {

    }
    public static MemberController getInstance(){
        return instance;
    }

    public Member searchMemberById(long memberId){
        Member member = null;
        try {
            member = memberService.findById(memberId);
        } catch (SQLException e) {
            FailView.errorMessage(e.getMessage());
        }
        return member;
    }

    public void registerMember(Member member){
        try {
            memberService.registerMember(member);
            SuccessView.printMessage("회원가입 완료");
        } catch (SQLException | EmailDuplicateException e) {
            FailView.errorMessage(e.getMessage());
        }
    }

    public boolean emailDuplicateChk(String email){
        boolean result = false;
        try {
            result = memberService.emailDuplicateChk(email);
        } catch (SQLException e) {
            FailView.errorMessage(e.getMessage());
        }
        return result;
    }

    public boolean passwordChk(Member member, String password){
        return memberService.passwordChk(member, password);
    }

    public Member login(Member member){
        Member login = null;
        try {
            login = memberService.login(member);
            SuccessView.printMessage("로그인 성공");
        } catch (SQLException | MemberNotFoundException e) {
            FailView.errorMessage(e.getMessage());
        }
        return login;
    }

    public void logout(Member member){
        memberService.logout(member);
        SuccessView.printMessage(member.getEmail()+"로그아웃");
    }

    public Member updatePassword(Member member, String password){
        Member updated = null;
        try {
            memberService.updatePassword(member, password);
            SuccessView.printMessage("비밀번호 변경 성공");
        } catch (SQLException | MemberNotFoundException e) {
            FailView.errorMessage(e.getMessage());
        }
        return updated;
    }
    public Member updateBalance(Member member){
        Member updated = null;
        try {
            updated = memberService.updateBalance(member);
            if(member.getBalance() < 0){
                SuccessView.printMessage(member.getBalance() + " 결제 완료");
            }else {
                SuccessView.printMessage(member.getBalance() + " 충전 완료");
            }

        } catch (SQLException | InsufficientBalanceException | MemberNotFoundException e) {
            FailView.errorMessage(e.getMessage());
        }
        return updated;
    }

    public List<CreditHistory> viewAllMemberChargeDetail(long memberId){
        List<CreditHistory> list = null;
        try {
            list = memberService.viewAllMemberChargeDetail(memberId);
        } catch (SQLException e) {
            FailView.errorMessage(e.getMessage());
        }
        return list;
    }



}
