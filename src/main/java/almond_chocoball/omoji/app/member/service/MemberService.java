package almond_chocoball.omoji.app.member.service;

import almond_chocoball.omoji.app.auth.dto.CustomUserDetails;
import almond_chocoball.omoji.app.member.dto.ProfileUpdateDto;
import almond_chocoball.omoji.app.member.entity.Member;

public interface MemberService {
    Member findMember(CustomUserDetails member);
    Member findMember(String socialId);
    Member findMember(Long id);

    ProfileUpdateDto updateProfile(CustomUserDetails member, String nickname);
}
