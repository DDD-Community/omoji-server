package almond_chocoball.omoji.app.member.service;

import almond_chocoball.omoji.app.auth.dto.CustomUserDetails;
import almond_chocoball.omoji.app.member.entity.Member;

public interface MemberService {
    public Member findMember(CustomUserDetails member);
}
