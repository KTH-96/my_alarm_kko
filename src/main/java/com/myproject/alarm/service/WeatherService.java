package com.myproject.alarm.service;

import static com.myproject.alarm.utils.Constant.*;

import com.myproject.alarm.domain.weather.WeatherInfo;
import com.myproject.alarm.utils.Constant;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeatherService {

    private WebDriver driver;

    public WeatherInfo crawlingWeatherData() {
//        Path path = Paths.get("src", "main", "resources", "chrome", "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",
                "/Users/taehyun/IdeaProjects/alarm/src/main/resources/chrome/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-popup-blocking");//팝업안띄움
        chromeOptions.addArguments("headless");//브라우저 안띄움
        chromeOptions.addArguments("--disable-gpu");//gpu 비활성화
        chromeOptions.addArguments("--blink-settings=imagesEnabled=false");//이미지 다운 안받음


        driver = new ChromeDriver(chromeOptions);

        //TODO: 예외처리 필요
        WeatherInfo weatherInfo = getWeatherInfo();

        driver.close();
        driver.quit();
        return weatherInfo;
    }

    private WeatherInfo getWeatherInfo() {
        driver.get(WEATHER_DAEJEON_URL);
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driverWait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("search"))
        );

        WebElement nowTemperature = driver.findElement(By.id("wob_tm"));
        log.info("현재 날씨 = {}", nowTemperature.getText());
        WebElement todayTemperature = driver.findElement(By.id("wob_dp"))
                .findElement(By.className("wNE31c"));
        log.info("오늘 최고 최저 온도 = {}", todayTemperature.getText());

        return WeatherInfo.builder()
                .nowTemperature(nowTemperature.getText())
                .todayTemperature(todayTemperature.getText())
                .build();
    }
}
