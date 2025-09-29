package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private static List<String> readAllLines(String resource) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(resource)) {
            if (inputStream == null){
                throw new FileNotFoundException("Resource not found: " + resource);
        }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            return reader
                    .lines()
                    .collect(Collectors.toList());
            }
        }
    }

    @GetMapping("/")
    public ModelAndView quote() {
        ModelAndView modelAndView = new ModelAndView("quote");

        try {
            List<String> quotes = readAllLines("citaty.txt");

            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(quotes.size());

            modelAndView.addObject("quote", quotes.get(index));
            modelAndView.addObject("image", index + 1);

    } catch (IOException e) {
            modelAndView.addObject("quote", "Citát se nepodařilo najít");
        }

        return modelAndView;
    }
}
