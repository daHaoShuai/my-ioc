package com.da;

import com.da.frame.annotation.Component;
import com.da.frame.annotation.Value;

/**
 * @author Da
 * @description xxx
 * @date 2022-07-28 12:09
 */
@Component
public class Config {
    @Value("${title}")
    private String title;
    @Value("${width}")
    private Double width;
    @Value("${height}")
    private Double height;

    public String getTitle() {
        return title;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }
}
