package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{
    private  MemberRepository memberRepository;
    private  DiscountPolicy discountPolicy;


    public MemberRepository getMemberRepository(){
        return memberRepository;
    }

    @Autowired
    public DiscountPolicy setDiscountPolicy
            (@MainDiscountPolicy DiscountPolicy discountPolicy) {
         this.discountPolicy = discountPolicy;
        return discountPolicy;
    }

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository,
                            @Qualifier("mainDiscountPolicy") DiscountPolicy
                                    discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    @Bean
    public OrderService orderService(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        return new OrderServiceImpl(memberRepository,discountPolicy);
    }

}
