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
public class YahooAuthController {
    private final YahooAuthService yahooAuthService;

    public YahooAuthController(YahooAuthService yahooAuthService) {
        this.yahooAuthService = yahooAuthService;
    }

    @GetMapping("/yahoo/oauth")
    public void initOAuth(HttpServletResponse response) throws IOException {
        URI yahooAuthUrl = yahooAuthService.buildAuthUrl();
        response.sendRedirect(yahooAuthUrl.toString());
    }

    @GetMapping("/yahoo/callback")
    public Mono<YahooTokenResponseDTO> handleYahooOAuthCallback(@RequestParam String code) {
        return yahooAuthService.exchangeCodeForToken(code);
    }
}
