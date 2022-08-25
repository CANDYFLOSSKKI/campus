package com.campus.dataset.Article.Entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_article")
public class Article {
    @TableId(value="ID")
    private Integer ID;
    private String owner;
    private String plate;
    private String title;
    private String content;
    @TableField("isAd")
    private boolean isAd;
    @TableField("lastUpdateTime")
    private LocalDateTime lastUpdateTime;
    @TableField(exist=false)
    private List<ArticleReply> reply;
}
