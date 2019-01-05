package com.yd.feign;

import feign.Feign;
import feign.Param;
import feign.RequestLine;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

import java.util.List;

/**
 * @author Yd on  2018-03-07
 * @description
 **/
public class GitHubServiceTest {

    public static void main(String[] args) {
        GitHub github = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(GitHub.class, "https://api.github.com");

        // Fetch and print a list of the contributors to this library.
        List<Contributor> contributors = github.contributors("Zeb-D", "my-api");
        for (Contributor contributor : contributors) {
            System.out.println(contributor.login + " (" + contributor.contributions + ")");
        }
    }

    interface GitHub {
        @RequestLine("GET /repos/{owner}/{repo}/contributors")
        List<Contributor> contributors(@Param("owner") String owner, @Param("repo") String repo);

    }

//    interface GitHub1 {
//        @GET @Path("/repos/{owner}/{repo}/contributors")
//        List<Contributor> contributors(@PathParam("owner") String owner, @PathParam("repo") String repo);
//    }

    static class Contributor {
        String login;
        int contributions;
    }

}
