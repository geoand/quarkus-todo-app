package io.quarkus.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PodHostResource
 */
@RestController
@RequestMapping("/podhost")
public class PodHostController {
  private String HOSTNAME =
  System.getenv().getOrDefault("HOSTNAME", "unknown");

  private int count = 0;

  @GetMapping
  public String getInformation() {
    return "Hostv3: " + HOSTNAME + " " + count++;
  } 
  
}
