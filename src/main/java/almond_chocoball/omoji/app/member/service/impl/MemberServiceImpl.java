package almond_chocoball.omoji.app.member.service.impl;

import almond_chocoball.omoji.app.auth.dto.CustomUserDetails;
import almond_chocoball.omoji.app.member.dto.ProfileUpdateDto;
import almond_chocoball.omoji.app.member.entity.Member;
import almond_chocoball.omoji.app.member.repository.MemberRepository;
import almond_chocoball.omoji.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public Member findMember(CustomUserDetails member) {
        return memberRepository.findBysocialId(member.getSocialId())
                .orElseThrow(() -> new NoSuchElementException("User Not Found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMember(String socialId) {
        return memberRepository.findBysocialId(socialId)
                .orElseThrow(() -> new NoSuchElementException("User Not Found"));
    }

    @Override
    public ProfileUpdateDto updateProfile(CustomUserDetails member, String nickname) {
        Member findMember = findMember(member);
        findMember.setNickname(nickname);
        return new ProfileUpdateDto(findMember.getNickname());
    }
}
