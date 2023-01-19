package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;
public class MemoryMemberRepository implements MemberRepository{

    /* 메모리에 저장되어야 하므로 회원이 저장되는 Map을 생성
    실무에 있어선 static으로 서로 공유되는 변수에는 동시성 문제가 발생할 수 있어
    ConcurrentHashMap을 사용하지만, 지금은 예제이기 때문에 일반 HashMap을 사용. */
    private static Map<Long, Member> store = new HashMap<>();

    private static long sequence = 0L; //현재 예제에서는 회원은 이름을 가지고 가입하므로 id는 시스템에서 생성해주기위해 sequence변수를 생성함.

    @Override
    public Member save(Member member) {
        member.setId(++sequence); //repo까지 왔을 때 member는 이미 name이 있으므로 setId(++sequence)로 id를 생성
        store.put(member.getId(),member); //메모리에 올리기 위해 Map에 해당 회원을 저장함.

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
       return store.values().stream()
               .filter(member -> member.getName().equals(name))
               .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
