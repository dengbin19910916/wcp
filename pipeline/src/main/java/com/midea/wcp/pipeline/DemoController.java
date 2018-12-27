package com.midea.wcp.pipeline;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
//import java.util.stream.Stream;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final Brickie brickie;

    public DemoController(Brickie brickie) {
        this.brickie = brickie;
    }

    /*@GetMapping("/write")
    public void write(@RequestParam String tableName) {
        String[] tableNames = tableName.split(",");
        Stream.of(tableNames).parallel().forEach(table-> {
            try {
                brickie.toEs(table);
            } catch (ClassNotFoundException | SQLException | IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }*/
}
