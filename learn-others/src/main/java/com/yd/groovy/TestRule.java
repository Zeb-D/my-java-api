package com.yd.groovy;

import com.deliveredtechnologies.rulebook.Fact;
import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.model.runner.RuleBookRunner;

/**
 * Created by Zeb灬D on 2017/12/18
 * Description:
 */
public class TestRule {
    public static void main(String[] args) {
//        RuleBook ruleBook = RuleBookBuilder.create()
//                .addRule(rule -> rule.withNoSpecifiedFactType()
//                        .then(f -> System.out.print("Hello "))
//                        .then(f -> System.out.println("World")))
//                .build();
//
//        ruleBook.run(new FactMap());

//        RuleBook ruleBook = RuleBookBuilder.create()
//                .addRule(rule -> rule.withFactType(String.class)
//                        .when(f -> f.containsKey("hello") && f.containsKey("world"))
//                        .using("hello").then(System.out::print )
//                        .using("world").then(System.out::println))
//                .build();
//
//        NameValueReferableMap factMap = new FactMap();
//        factMap.setValue("hello", "Hello ");
//        factMap.setValue("world", " World");
//        ruleBook.run(factMap);

//        RuleBook homeLoanRateRuleBook = RuleBookBuilder.create(HomeLoanRateRuleBook.class).withResultType(Double.class)
//                .withDefaultResult(4.5)
//                .build();
//        NameValueReferableMap facts = new FactMap();
//        facts.setValue("applicant", new ApplicantBean(650, 20000.0, true));
//        homeLoanRateRuleBook.run(facts);
//
//        homeLoanRateRuleBook.getResult().ifPresent(result -> System.out.println("Applicant qualified for the following rate: " + result));

        RuleBookRunner ruleBook = new RuleBookRunner("com.example.rulebook.megabank");
        NameValueReferableMap<ApplicantBean> facts = new FactMap<>();
        ApplicantBean applicant1 = new ApplicantBean(650, 20000, true);
        ApplicantBean applicant2 = new ApplicantBean(620, 30000, true);
        facts.put(new Fact<>(applicant1));
        facts.put(new Fact<>(applicant2));

        ruleBook.setDefaultResult(4.5);
        ruleBook.run(facts);
        ruleBook.getResult().ifPresent(result -> System.out.println("Applicant qualified for the following rate: " + result));

    }
}