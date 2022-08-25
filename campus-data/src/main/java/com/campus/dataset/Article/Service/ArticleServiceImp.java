package com.campus.dataset.Article.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.campus.common.Entity.Resp;
import com.campus.common.Entity.RespContainData;
import com.campus.common.Service.LoginInfoService;
import com.campus.dataset.Article.Entity.Article;
import com.campus.dataset.Article.Entity.ArticleDoc;
import com.campus.dataset.Article.Mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

@Service
public class ArticleServiceImp extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper mapper;
    @Resource
    private LoginInfoService loginInfoService;

    @Override
    public ArticleDoc fillPlate(ArticleDoc info){
        if(info.getPlate()==null) {
            info.setPlate("校园广场");
        }else if(!info.getPlate().equals("校园广场")&&!info.getPlate().equals(loginInfoService.getLoginSchool())&&!loginInfoService.getLoginType().equals("admin")){
            info.setPlate(loginInfoService.getLoginSchool());
        }
        return info;
    }

    @Override
    public Resp setAdArticle(ArticleDoc info) {
        info=fillPlate(info);
        Article text=getArticleByPlateAndTitle(info);
        if(text==null){
            return new Resp(false,"对应的文章不存在");
        }
        text.setAd(true);
        LambdaUpdateWrapper<Article> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(Article::getTitle,text.getTitle()).eq(Article::getPlate,info.getPlate());
        mapper.update(text,wrapper);
        return new Resp(true,"成功设置文章'"+info.getTitle()+"'为创作推广,将被优先展示");
    }

    @Override
    public Resp updateArticle(ArticleDoc info) {
        info=fillPlate(info);
        Article text=getArticleByPlateAndTitle(info);
        if(text==null){
            return new Resp(false,"对应的文章不存在");
        }
        if(loginInfoService.getLoginType().equals("user")&&!text.getOwner().equals(loginInfoService.getLoginName())){
            return new Resp(false,"权限不足,不能修改其他用户的文章");
        }
        text.setContent(info.getContent());
        LambdaUpdateWrapper<Article> wrapper=new LambdaUpdateWrapper<>();
        wrapper.eq(Article::getTitle,text.getTitle()).eq(Article::getPlate,info.getPlate());
        mapper.update(text,wrapper);
        return new Resp(true,"文章'"+info.getTitle()+"'修改提交成功");
    }

    @Override
    public Resp delArticle(ArticleDoc info){
        info=fillPlate(info);
        Article text=getArticleByPlateAndTitle(info);
        if(text==null){
            return new Resp(false,"对应的文章不存在");
        }
        if(loginInfoService.getLoginType().equals("user")&&!text.getOwner().equals(loginInfoService.getLoginName())){
            return new Resp(false,"权限不足,不能删除其他用户的文章");
        }
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Article::getTitle,text.getTitle()).eq(Article::getPlate,info.getPlate());
        mapper.delete(wrapper);
        return new Resp(true,"文章'"+info.getTitle()+"'已被删除");
    }

    @Override
    public Article getArticleByPlateAndTitle(ArticleDoc info) {
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Article::getTitle,info.getTitle()).eq(Article::getPlate,info.getPlate());
        return mapper.selectOne(wrapper);
    }

    @Override
    public Resp addArticle(ArticleDoc info) {
        info=fillPlate(info);
        if(getArticleByPlateAndTitle(info)==null){
            mapper.insert(new Article(0,loginInfoService.getLoginName(),info.getPlate(),info.getTitle(),info.getContent(),false,null,null));
            return new Resp(true,"文章'"+info.getTitle()+"'在'"+info.getPlate()+"'板块发布成功");
        }
        return new Resp(false,"该板块已存在同标题文章,发布失败");
    }


    @Override
    public List<Article> showArticleFilter(List<Article> list) {
        return list.stream().peek(article->article.setContent(article.getContent().substring(0,20).concat("...")))
                .sorted(new Comparator<>() {
                            @Override
                            public int compare(Article o1, Article o2) {
                                int o1Rank = 0, o2Rank = 0;
                                if (o1.isAd()) {o1Rank += 10;}
                                if (o2.isAd()) {o2Rank += 10;}
                                if (o1.getPlate().equals("公告栏")) {o1Rank += 2;}
                                if (o2.getPlate().equals("公告栏")) {o2Rank += 2;}
                                if (o1.getPlate().equals("校园广场")) {o1Rank++;}
                                if (o2.getPlate().equals("校园广场")) {o2Rank++;}
                                return Integer.compare(o1Rank, o2Rank);
                            }
                        }
        ).toList();
    }

    @Override
    public RespContainData showManageArticle(){
        List<Article> list=mapper.selectList(new LambdaQueryWrapper<>());
        if(list.size()==0){
            return new RespContainData(false,"文章数据为空",null);
        }
        list=showArticleFilter(list);
        return new RespContainData(true,"共查找到"+list.size()+"篇文章,以下是各文章数据",list);
    }

    @Override
    public RespContainData showPlateArticle(){
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        wrapper.in(Article::getPlate, new String[]{loginInfoService.getLoginPlate(), "公告栏"});
        List<Article> list=mapper.selectList(wrapper);
        if(list.size()==0){
            return new RespContainData(false,"文章数据为空",null);
        }
        list=showArticleFilter(list);
        return new RespContainData(true,"共查找到"+list.size()+"篇文章,以下是各文章数据",list);
    }

    @Override
    public Resp shiftIntoText(String title) {
        Article article=getArticleByPlateAndTitle(new ArticleDoc(loginInfoService.getLoginPlate(),title,null));
        if(article==null){
            return new Resp(false,"未找到匹配的文章");
        }
        loginInfoService.setLoginTextID(article.getID());
        return new Resp(true,"成功进入文章:"+article.getTitle()+"浏览页面");
    }
}
