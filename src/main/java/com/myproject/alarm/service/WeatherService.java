package com.myproject.alarm.service;

import static com.myproject.alarm.utils.Constant.WEATHER_DAEJEON_URL;

import com.myproject.alarm.domain.weather.WeatherInfo;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
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
        System.setProperty("webdriver.chrome.driver",
                "/Users/taehyun/IdeaProjects/alarm/src/main/resources/chrome/chromedriver");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--disable-popup-blocking");//팝업안띄움
        chromeOptions.addArguments("headless");//브라우저 안띄움
        chromeOptions.addArguments("--disable-gpu");//gpu 비활성화
        chromeOptions.addArguments("--blink-settings=imagesEnabled=false");//이미지 다운 안받음

        driver = new ChromeDriver(chromeOptions);

        WeatherInfo weatherInfo = getWeatherInfo();

        driver.close();
        driver.quit();
        return weatherInfo;
    }

    private WeatherInfo getWeatherInfo() {
        driver.get(WEATHER_DAEJEON_URL);
        WebDriverWait driverWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driverWait.until(
                ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        );

        WebElement nowTemperature = driver.findElement(By.xpath(
                "//*[@id=\"main_pack\"]/section[1]/div[1]/div[2]/div[1]/div[1]/div/"
                        + "div[2]/div/div[1]/div[1]/div[2]/strong"));
        log.info("현재 날씨 = {}", nowTemperature.getText());
        WebElement todayTemperature = driver.findElement(By.xpath(
                "//*[@id=\"main_pack\"]/section[1]/div[1]/div[2]/div[5]/div[1]/div/"
                        + "div[2]/ul/li[1]/div/div[3]/span"));
        log.info("오늘 최고 최저 온도 = {}", todayTemperature.getText());

        return WeatherInfo.builder()
                .nowTemperature(nowTemperature.getText())
                .todayTemperature(todayTemperature.getText())
                .build();
    }
}
