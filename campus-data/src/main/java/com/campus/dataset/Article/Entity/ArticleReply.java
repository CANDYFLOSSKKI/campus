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
@TableName("tb_reply")
public class ArticleReply {
    @TableId(value="ID")
    private Integer ID;
    private String owner;
    private String content;
    @TableField("textID")
    private Integer textID;
    @TableField("replyID")
    private Integer replyID;
    @TableField("postTime")
    private LocalDateTime postTime;
}
