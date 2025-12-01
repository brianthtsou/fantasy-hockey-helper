package com.fantasyhockeyhelper.fantasy_hockey_helper_server;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RestController
public class ExternalYahooAPIService {
  @Value("${api.key}")
  private String apiKey;

  private final WebClient oAuthClient = WebClient
      .create("https://api.login.yahoo.com/oauth2/request_auth");

  @GetMapping("/yahoo/oauth")
  public URI initOAuth() {
    MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
    System.out.println(apiKey);
    request.add("client_id", apiKey);
    request.add("redirect_uri", "oob");
    request.add("response_type", "code");

    return UriComponentsBuilder
        .fromUriString("https://api.login.yahoo.com/oauth2/request_auth")
        .queryParams(request)
        .build(true).toUri();
  }

}
