package milf.eventmicroservice.controller;

import lombok.RequiredArgsConstructor;
import milf.eventmicroservice.service.EventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("/test")
    public Mono<Void> test(){
        return eventService.sendMessage();
    }

}
