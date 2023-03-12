package jpabook3.jpashop3.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook3.jpashop3.domain.Member;
import jpabook3.jpashop3.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id  );
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        Long id = memberService.join(member);
        return new CreateMemberResponse(id  );
    }

    @Data
    static class CreateMemberRequest{
        private String name;
    }
    @Data
    static class CreateMemberResponse{
        private Long id;
        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @PatchMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id,
                                               @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName());

        return new UpdateMemberResponse(id,request.getName());
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;

        public UpdateMemberResponse(String name) {
            this.name = name;
        }
    }
    @Data
    static class UpdateMemberRequest{
        private String name;
    }


    @GetMapping("/api/v1/members")
    public List<Member> listV1(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){

        return new Result(memberService.findMembers()
                .stream().map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList()));

    }
    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }


    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }






}
