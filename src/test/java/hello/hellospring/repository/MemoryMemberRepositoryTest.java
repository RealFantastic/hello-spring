package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repo = new MemoryMemberRepository();
    @AfterEach
    public void afterEach(){
        repo.clearStore();
    }
    @Test
    public void save(){
        Member member = new Member();
        member.setName("박정환");

        repo.save(member);

        Member result = repo.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }
    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("박정환1");
        repo.save(member1);

        Member member2 = new Member();
        member2.setName("박정환2");
        repo.save(member2);

        Member result = repo.findByName("박정환1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("박정환1");
        repo.save(member1);

        Member member2 = new Member();
        member2.setName("김혜린");
        repo.save(member2);

        Member member3 = new Member();
        member3.setName("윤영원");
        repo.save(member3);

        Member member4 = new Member();
        member4.setName("전승희");
        repo.save(member4);

        List<Member> result = repo.findAll();

        assertThat(result.size()).isEqualTo(4);

    }
}
