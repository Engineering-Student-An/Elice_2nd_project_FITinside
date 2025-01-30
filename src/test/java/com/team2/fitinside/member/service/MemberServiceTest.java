package com.team2.fitinside.member.service;

import com.team2.fitinside.config.SecurityUtil;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import com.team2.fitinside.member.controller.MemberController;
import com.team2.fitinside.member.dto.MemberListResponse;
import com.team2.fitinside.member.dto.MemberResponseDto;
import com.team2.fitinside.member.entity.Member;
import com.team2.fitinside.member.mapper.MemberMapper;
import com.team2.fitinside.member.repository.MemberRepository;
import com.team2.fitinside.order.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @InjectMocks
    private MemberService memberService; // 실제 테스트할 서비스 클래스

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private MemberMapper memberMapper;

    private Member member;
    private MemberResponseDto responseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // 테스트에 사용할 Member 및 MemberResponseDto 초기화
        member = Member.builder()
                .id(1L)
                .email("test@example.com")
                .userName("test")
                .password("test")
                .build();

        responseDto = new MemberResponseDto();
        responseDto.setId(1L);
        responseDto.setEmail("test@example.com");
        responseDto.setUserName("test");
    }

    @Test
    @DisplayName("로그인 유저의 본인 정보 조회 성공 테스트")
    void testGetMyInfoBySecurity_Success() {
        // given

        when(securityUtil.getCurrentMemberId()).thenReturn(1L); // mock: 현재 사용자 ID 반환
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member)); // mock: 해당 사용자 찾음
        when(memberMapper.memberToResponse(member)).thenReturn(responseDto); // mock: 사용자를 MemberResponseDto로 변환

        // when
        MemberResponseDto result = memberService.getMyInfoBySecurity();

        // then
        assertEquals(responseDto, result); // 응답이 예상한 MemberResponseDto인지 확인
        verify(securityUtil, times(1)).getCurrentMemberId(); // getCurrentMemberId()가 한 번 호출되었는지 확인
        verify(memberRepository, times(1)).findById(1L); // findById()가 한 번 호출되었는지 확인
        verify(memberMapper, times(1)).memberToResponse(member); // memberToResponse()가 한 번 호출되었는지 확인
    }

    @Test
    @DisplayName("로그인 유저의 본인 정보 조회 실패 테스트 - Not Found 404")
    void testGetMyInfoBySecurity_UserNotFound() {
        // given

        when(securityUtil.getCurrentMemberId()).thenReturn(1L); // mock: 현재 사용자 ID 반환
        when(memberRepository.findById(1L)).thenReturn(Optional.empty()); // mock: 사용자를 찾지 못함

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            memberService.getMyInfoBySecurity();
        });

        // then
        assertEquals(ErrorCode.USER_NOT_FOUND, thrown.getErrorCode()); // 예외가 USER_NOT_FOUND인지 확인
        verify(securityUtil, times(1)).getCurrentMemberId(); // getCurrentMemberId()가 한 번 호출되었는지 확인
        verify(memberRepository, times(1)).findById(1L); // findById()가 한 번 호출되었는지 확인
        verify(memberMapper, times(0)).memberToResponse(any()); // memberToResponse()가 호출되지 않았는지 확인
    }

    @Test
    @DisplayName("회원 이름 변경 성공 테스트")
    void testChangeMemberUserName_Success() {
        // given
        String newUserName = "newUserName";
        responseDto.setUserName(newUserName);

            // 현재 사용자의 ID 반환
        when(securityUtil.getCurrentMemberId()).thenReturn(1L);

            // 사용자가 있는 경우
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

            // 사용자 정보가 업데이트된 후 저장된 결과 반환
        when(memberRepository.save(any(Member.class))).thenReturn(member);

            // Member -> MemberResponseDto로 매핑
        when(memberMapper.memberToResponse(member)).thenReturn(responseDto);

        // when
        MemberResponseDto result = memberService.changeMemberUserName(newUserName);

        // then
        Assertions.assertEquals("newUserName", result.getUserName()); // 응답에서 새로운 이름이 반환되었는지 확인
        verify(securityUtil, times(1)).getCurrentMemberId(); // getCurrentMemberId() 호출 확인
        verify(memberRepository, times(1)).findById(1L); // findById() 호출 확인
        verify(memberRepository, times(1)).save(member); // save() 호출 확인
        verify(memberMapper, times(1)).memberToResponse(member); // memberToResponse() 호출 확인
    }

    @Test
    @DisplayName("회원 이름 변경 실패 테스트 - Not Found 404")
    void testChangeMemberUserName_UserNotFound() {
        // given
        String newUserName = "newUserName";
        responseDto.setUserName(newUserName);

        // 현재 사용자의 ID 반환
        when(securityUtil.getCurrentMemberId()).thenReturn(1L);

        // 사용자를 찾지 못한 경우
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            memberService.changeMemberUserName(newUserName);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND, thrown.getErrorCode()); // 예외의 오류 코드가 USER_NOT_FOUND인지 확인
        verify(securityUtil, times(1)).getCurrentMemberId(); // getCurrentMemberId() 호출 확인
        verify(memberRepository, times(1)).findById(1L); // findById() 호출 확인
        verify(memberRepository, times(0)).save(any(Member.class)); // save()는 호출되지 않아야 함
        verify(memberMapper, times(0)).memberToResponse(any()); // memberToResponse()는 호출되지 않아야 함
    }

    @Test
    @DisplayName("회원 전화번호 변경 성공 테스트")
    void changeMemberPhone() {
        // given
        String newPhone = "000-0000-0000";
        responseDto.setPhone(newPhone);

        // 현재 사용자의 ID 반환
        when(securityUtil.getCurrentMemberId()).thenReturn(1L);

        // 사용자가 있는 경우
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // 사용자 정보가 업데이트된 후 저장된 결과 반환
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // Member -> MemberResponseDto로 매핑
        when(memberMapper.memberToResponse(member)).thenReturn(responseDto);

        // when
        MemberResponseDto result = memberService.changeMemberPhone(newPhone);

        // then
        Assertions.assertEquals(newPhone, result.getPhone()); // 응답에서 새로운 이름이 반환되었는지 확인
        verify(securityUtil, times(1)).getCurrentMemberId(); // getCurrentMemberId() 호출 확인
        verify(memberRepository, times(1)).findById(1L); // findById() 호출 확인
        verify(memberRepository, times(1)).save(member); // save() 호출 확인
        verify(memberMapper, times(1)).memberToResponse(member); // memberToResponse() 호출 확인
    }

    @Test
    @DisplayName("회원 탈퇴 기능 성공 테스트")
    void testDeleteMember_Success() {
        // given
        // 현재 사용자의 ID 반환
        when(securityUtil.getCurrentMemberId()).thenReturn(1L);

        // 사용자가 존재할 때, ID로 조회되도록 설정
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // Member -> MemberResponseDto로 매핑
        when(memberMapper.memberToResponse(member)).thenReturn(responseDto);

        // when
        MemberResponseDto result = memberService.deleteMember();

        // then
        assertEquals(member.getId(), result.getId()); // 삭제된 사용자의 ID 확인
        assertEquals(member.getEmail(), result.getEmail()); // 삭제된 사용자의 이메일 확인
        verify(securityUtil, times(1)).getCurrentMemberId(); // getCurrentMemberId() 호출 확인
        verify(memberRepository, times(1)).findById(1L); // findById() 호출 확인
        verify(memberRepository, times(1)).delete(member); // delete() 호출 확인
        verify(memberMapper, times(1)).memberToResponse(member); // memberToResponse() 호출 확인
    }

    @Test
    @DisplayName("회원 탈퇴 기능 실패 테스트 - Not Found 404")
    void testDeleteMember_UserNotFound() {
        // given
        // 현재 사용자의 ID 반환
        when(securityUtil.getCurrentMemberId()).thenReturn(1L);

        // 사용자를 찾지 못했을 때 빈 결과 반환
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            memberService.deleteMember();
        });

        // 예외의 오류 코드가 USER_NOT_FOUND인지 확인
        assertEquals(ErrorCode.USER_NOT_FOUND, thrown.getErrorCode());
        verify(securityUtil, times(1)).getCurrentMemberId(); // getCurrentMemberId() 호출 확인
        verify(memberRepository, times(1)).findById(1L); // findById() 호출 확인
        verify(memberRepository, times(0)).delete(any(Member.class)); // delete()는 호출되지 않아야 함
        verify(memberMapper, times(0)).memberToResponse(any()); // memberToResponse()는 호출되지 않아야 함
    }

    @Test
    @DisplayName("관리자 - 회원 조회 성공 테스트")
    void testGetMemberList_Success() {
        // given
        int page = 1; // 첫 번째 페이지 요청
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").ascending());

        List<Member> memberList = new ArrayList<>();
        memberList.add(member); // 테스트할 멤버 추가

        // 페이징된 결과를 담은 Page 객체 생성
        Page<Member> pagedMembers = new PageImpl<>(memberList, pageRequest, 1);

        // 멤버 리스트를 반환하도록 설정
        when(memberRepository.findAll(pageRequest)).thenReturn(pagedMembers);

        // Member -> MemberResponseDto 변환 설정
        when(memberMapper.memberToResponse(member)).thenReturn(responseDto);

        // when
        MemberListResponse result = memberService.getMemberList(page);

        // then
        assertEquals(1, result.getTotalPages()); // 페이지 수 확인
        assertEquals(1, result.getMemberList().size()); // 반환된 멤버 수 확인
        assertEquals(responseDto.getUserName(), result.getMemberList().get(0).getUserName()); // 멤버 정보 확인

        verify(memberRepository, times(1)).findAll(pageRequest); // findAll 호출 확인
        verify(memberMapper, times(1)).memberToResponse(member); // memberToResponse 호출 확인
    }

    @Test
    @DisplayName("관리자 - 회원 조회 실패 테스트 - 빈페이지")
    void testGetMemberList_Empty() {
        // given
        int page = 1; // 첫 번째 페이지 요청
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").ascending());

        List<Member> memberList = new ArrayList<>(); // 빈 멤버 리스트

        // 빈 Page 객체 생성
        Page<Member> pagedMembers = new PageImpl<>(memberList, pageRequest, 0);

        // 멤버 리스트를 반환하도록 설정
        when(memberRepository.findAll(pageRequest)).thenReturn(pagedMembers);

        // when
        MemberListResponse result = memberService.getMemberList(page);

        // then
        assertEquals(1, result.getTotalPages()); // 총 페이지 수는 최소 1이어야 함
        assertTrue(result.getMemberList().isEmpty()); // 멤버 리스트는 비어 있어야 함

        verify(memberRepository, times(1)).findAll(pageRequest); // findAll 호출 확인
        verify(memberMapper, times(0)).memberToResponse(any()); // memberToResponse는 호출되지 않아야 함
    }

    @Test
    @DisplayName("관리자 - 회원 탈퇴 성공 테스트")
    void testDeleteMemberByMemberId_Success() {
        // given
        Long memberId = 1L;

        // memberId로 멤버를 찾았을 때 성공적으로 반환되도록 설정
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        memberService.deleteMemberByMemberId(memberId);

        // then
        verify(memberRepository, times(1)).findById(memberId); // findById 호출 확인
        verify(memberRepository, times(1)).delete(member); // delete 호출 확인
    }

    @Test
    @DisplayName("관리자 - 회원 탈퇴 실패 테스트 - Not Found 404")
    void testDeleteMemberByMemberId_UserNotFound() {
        // given
        Long memberId = 1L;

        // memberId로 멤버를 찾지 못했을 때 빈 결과 반환
        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        // when & then
        CustomException thrown = assertThrows(CustomException.class, () -> {
            memberService.deleteMemberByMemberId(memberId);
        });

        // 예외의 오류 코드가 USER_NOT_FOUND인지 확인
        assertEquals(ErrorCode.USER_NOT_FOUND, thrown.getErrorCode());

        verify(memberRepository, times(1)).findById(memberId); // findById 호출 확인
        verify(memberRepository, times(0)).delete(any(Member.class)); // delete는 호출되지 않아야 함
    }

    @Test
    @DisplayName("관리자 - 탈퇴 회원 조회 성공 테스트")
    void testGetIsDeleteMembers_Success() {
        // given
        member.setIsDeleted(true); // isDelete 값 설정
        int page = 1; // 첫 번째 페이지 요청
        Pageable pageRequest = PageRequest.of(page - 1, 10);

        List<Member> memberList = new ArrayList<>();
        memberList.add(member); // 테스트할 멤버 추가

        // 페이징된 결과를 담은 Page 객체 생성
        Page<Member> pagedMembers = new PageImpl<>(memberList, pageRequest, 1);

        // isDelete가 true인 멤버 리스트를 반환하도록 설정
        when(memberRepository.findAllByIsDeleteTrue(pageRequest)).thenReturn(pagedMembers);

        // Member -> MemberResponseDto 변환 설정
        when(memberMapper.memberToResponse(member)).thenReturn(responseDto);

        // when
        MemberListResponse result = memberService.getIsDeleteMembers(page);

        // then
        assertEquals(1, result.getTotalPages()); // 페이지 수 확인
        assertEquals(1, result.getMemberList().size()); // 반환된 멤버 수 확인
        assertEquals(responseDto.getUserName(), result.getMemberList().get(0).getUserName()); // 멤버 정보 확인

        verify(memberRepository, times(1)).findAllByIsDeleteTrue(pageRequest); // findAllByIsDeleteTrue 호출 확인
        verify(memberMapper, times(1)).memberToResponse(member); // memberToResponse 호출 확인
    }

    @Test
    @DisplayName("관리자 - 탈퇴 회원 조회 실패 테스트 - 빈 페이지")
    void testGetIsDeleteMembers_Empty() {
        // given
        member.setIsDeleted(true); // isDelete 값 설정
        int page = 1; // 첫 번째 페이지 요청
        Pageable pageRequest = PageRequest.of(page - 1, 10);

        List<Member> memberList = new ArrayList<>(); // 빈 멤버 리스트

        // 빈 Page 객체 생성
        Page<Member> pagedMembers = new PageImpl<>(memberList, pageRequest, 0);

        // isDelete가 true인 빈 멤버 리스트를 반환하도록 설정
        when(memberRepository.findAllByIsDeleteTrue(pageRequest)).thenReturn(pagedMembers);

        // when
        MemberListResponse result = memberService.getIsDeleteMembers(page);

        // then
        assertEquals(1, result.getTotalPages()); // 총 페이지 수는 최소 1이어야 함
        assertTrue(result.getMemberList().isEmpty()); // 멤버 리스트는 비어 있어야 함

        verify(memberRepository, times(1)).findAllByIsDeleteTrue(pageRequest); // findAllByIsDeleteTrue 호출 확인
        verify(memberMapper, times(0)).memberToResponse(any()); // memberToResponse는 호출되지 않아야 함
    }
    
}