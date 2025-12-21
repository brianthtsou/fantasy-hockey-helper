package com.fantasyhockeyhelper.fantasy_hockey_helper_server;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;
import reactor.core.publisher.Mono;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RestController
public class ExternalYahooAPIService {
  @Value("${api.key}")
  private String apiKey;

  @Value("${api.secret}")
  private String apiSecret;

  // private final WebClient oAuthClient = WebClient
  // .create("https://api.login.yahoo.com/oauth2/request_auth");

  private final WebClient webClient;

  public ExternalYahooAPIService(WebClient.Builder builder) {
    webClient = builder.baseUrl("https://api.login.yahoo.com/oauth2").build();
  }

  @GetMapping("/yahoo/oauth")
  public void initOAuth(HttpServletResponse response) throws IOException {
    MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
    System.out.println(apiKey);
    request.add("client_id", apiKey);
    request.add("redirect_uri", "https://condignly-malonyl-kristie.ngrok-free.dev/yahoo/callback");
    request.add("response_type", "code");

    URI yahooAuthUrl = UriComponentsBuilder
        .fromUriString("https://api.login.yahoo.com/oauth2/request_auth")
        .queryParams(request)
        .build(true)
        .encode()
        .toUri();

    System.out.println("Yahoo auth URL = " + yahooAuthUrl);

    response.sendRedirect(yahooAuthUrl.toString());
  }

  @GetMapping("/yahoo/callback")
  public Mono<String> handleYahooOAuthCallback(@RequestParam String code) {
    return webClient
        .post()
        .uri("/get_token")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(BodyInserters.fromFormData("client_id", apiKey)
            .with("client_secret", apiSecret)
            .with("redirect_uri", "https://condignly-malonyl-kristie.ngrok-free.dev/yahoo/callback")
            .with("code", code)
            .with("grant_type", "authorization_code"))
        .retrieve()
        .bodyToMono(String.class);
  }
}
