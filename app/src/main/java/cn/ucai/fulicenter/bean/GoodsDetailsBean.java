package cn.ucai.fulicenter.bean;

/**
 * Created by Administrator on 2016/10/13.
 */
public class GoodsDetailsBean {

    /**
     * id : 280
     * goodsId : 7677
     * catId : 291
     * goodsName : 双层分格饭盒 绿色
     * goodsEnglishName : Monbento
     * goodsBrief : PP食品级材质，轻巧、易清洗、蠕变性小，不易变形，可置于微波炉加热，可方巾洗碗机清洗。双层色彩可以随意组合，轻巧方便。
     * shopPrice : ￥253
     * currencyPrice : ￥293
     * promotePrice : ￥0
     * rankPrice : ￥293
     * isPromote : false
     * goodsThumb : 201509/thumb_img/7677_thumb_G_1442391216339.png
     * goodsImg : 201509/thumb_img/7677_thumb_G_1442391216339.png
     * addTime : 1442419200000
     * shareUrl : http://m.fulishe.com/item/7677
     * properties : null
     * promote : false
     */

    private int id;
    private int goodsId;
    private int catId;
    private String goodsName;
    private String goodsEnglishName;
    private String goodsBrief;
    private String shopPrice;
    private String currencyPrice;
    private String promotePrice;
    private String rankPrice;
    private boolean isPromote;
    private String goodsThumb;
    private String goodsImg;
    private long addTime;
    private String shareUrl;
    private PropertiesBean properties;
    private boolean promote;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsEnglishName() {
        return goodsEnglishName;
    }

    public void setGoodsEnglishName(String goodsEnglishName) {
        this.goodsEnglishName = goodsEnglishName;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getCurrencyPrice() {
        return currencyPrice;
    }

    public void setCurrencyPrice(String currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    public String getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(String promotePrice) {
        this.promotePrice = promotePrice;
    }

    public String getRankPrice() {
        return rankPrice;
    }

    public void setRankPrice(String rankPrice) {
        this.rankPrice = rankPrice;
    }

    public boolean isIsPromote() {
        return isPromote;
    }

    public void setIsPromote(boolean isPromote) {
        this.isPromote = isPromote;
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public PropertiesBean getProperties() {
        return properties;
    }

    public void setProperties(PropertiesBean properties) {
        this.properties = properties;
    }

    public boolean isPromote() {
        return promote;
    }

    public void setPromote(boolean promote) {
        this.promote = promote;
    }
}
