package com.boyoi.icon;

import com.boyoi.base.dao.IconDao;
import com.boyoi.base.domain.Icon;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 批量添加图标
 * @author Chenj
 */
public class BatchAdd {
    ApplicationContext context=null;
    String icon ;
    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(new String[]{"classpath*:/spring/spring_daos.xml"});
        icon = "glyphicon glyphicon-asterisk\n" +
                "glyphicon glyphicon-plus\n" +
                "glyphicon glyphicon-euro\n" +
                "glyphicon glyphicon-eur\n" +
                "glyphicon glyphicon-minus\n" +
                "glyphicon glyphicon-cloud\n" +
                "glyphicon glyphicon-envelope\n" +
                "glyphicon glyphicon-pencil\n" +
                "glyphicon glyphicon-glass\n" +
                "glyphicon glyphicon-music\n" +
                "glyphicon glyphicon-search\n" +
                "glyphicon glyphicon-heart\n" +
                "glyphicon glyphicon-star\n" +
                "glyphicon glyphicon-star-empty\n" +
                "glyphicon glyphicon-user\n" +
                "glyphicon glyphicon-film\n" +
                "glyphicon glyphicon-th-large\n" +
                "glyphicon glyphicon-th\n" +
                "glyphicon glyphicon-th-list\n" +
                "glyphicon glyphicon-ok\n" +
                "glyphicon glyphicon-remove\n" +
                "glyphicon glyphicon-zoom-in\n" +
                "glyphicon glyphicon-zoom-out\n" +
                "glyphicon glyphicon-off\n" +
                "glyphicon glyphicon-signal\n" +
                "glyphicon glyphicon-cog\n" +
                "glyphicon glyphicon-trash\n" +
                "glyphicon glyphicon-home\n" +
                "glyphicon glyphicon-file\n" +
                "glyphicon glyphicon-time\n" +
                "glyphicon glyphicon-road\n" +
                "glyphicon glyphicon-download-alt\n" +
                "glyphicon glyphicon-download\n" +
                "glyphicon glyphicon-upload\n" +
                "glyphicon glyphicon-inbox\n" +
                "glyphicon glyphicon-play-circle\n" +
                "glyphicon glyphicon-repeat\n" +
                "glyphicon glyphicon-refresh\n" +
                "glyphicon glyphicon-list-alt\n" +
                "glyphicon glyphicon-lock\n" +
                "glyphicon glyphicon-flag\n" +
                "glyphicon glyphicon-headphones\n" +
                "glyphicon glyphicon-volume-off\n" +
                "glyphicon glyphicon-volume-down\n" +
                "glyphicon glyphicon-volume-up\n" +
                "glyphicon glyphicon-qrcode\n" +
                "glyphicon glyphicon-barcode\n" +
                "glyphicon glyphicon-tag\n" +
                "glyphicon glyphicon-tags\n" +
                "glyphicon glyphicon-book\n" +
                "glyphicon glyphicon-bookmark\n" +
                "glyphicon glyphicon-print\n" +
                "glyphicon glyphicon-camera\n" +
                "glyphicon glyphicon-font\n" +
                "glyphicon glyphicon-bold\n" +
                "glyphicon glyphicon-italic\n" +
                "glyphicon glyphicon-text-height\n" +
                "glyphicon glyphicon-text-width\n" +
                "glyphicon glyphicon-align-left\n" +
                "glyphicon glyphicon-align-center\n" +
                "glyphicon glyphicon-align-right\n" +
                "glyphicon glyphicon-align-justify\n" +
                "glyphicon glyphicon-list\n" +
                "glyphicon glyphicon-indent-left\n" +
                "glyphicon glyphicon-indent-right\n" +
                "glyphicon glyphicon-facetime-video\n" +
                "glyphicon glyphicon-picture\n" +
                "glyphicon glyphicon-map-marker\n" +
                "glyphicon glyphicon-adjust\n" +
                "glyphicon glyphicon-tint\n" +
                "glyphicon glyphicon-edit\n" +
                "glyphicon glyphicon-share\n" +
                "glyphicon glyphicon-check\n" +
                "glyphicon glyphicon-move\n" +
                "glyphicon glyphicon-step-backward\n" +
                "glyphicon glyphicon-fast-backward\n" +
                "glyphicon glyphicon-backward\n" +
                "glyphicon glyphicon-play\n" +
                "glyphicon glyphicon-pause\n" +
                "glyphicon glyphicon-stop\n" +
                "glyphicon glyphicon-forward\n" +
                "glyphicon glyphicon-fast-forward\n" +
                "glyphicon glyphicon-step-forward\n" +
                "glyphicon glyphicon-eject\n" +
                "glyphicon glyphicon-chevron-left\n" +
                "glyphicon glyphicon-chevron-right\n" +
                "glyphicon glyphicon-plus-sign\n" +
                "glyphicon glyphicon-minus-sign\n" +
                "glyphicon glyphicon-remove-sign\n" +
                "glyphicon glyphicon-ok-sign\n" +
                "glyphicon glyphicon-question-sign\n" +
                "glyphicon glyphicon-info-sign\n" +
                "glyphicon glyphicon-screenshot\n" +
                "glyphicon glyphicon-remove-circle\n" +
                "glyphicon glyphicon-ok-circle\n" +
                "glyphicon glyphicon-ban-circle\n" +
                "glyphicon glyphicon-arrow-left\n" +
                "glyphicon glyphicon-arrow-right\n" +
                "glyphicon glyphicon-arrow-up\n" +
                "glyphicon glyphicon-arrow-down\n" +
                "glyphicon glyphicon-share-alt\n" +
                "glyphicon glyphicon-resize-full\n" +
                "glyphicon glyphicon-resize-small\n" +
                "glyphicon glyphicon-exclamation-sign\n" +
                "glyphicon glyphicon-gift\n" +
                "glyphicon glyphicon-leaf\n" +
                "glyphicon glyphicon-fire\n" +
                "glyphicon glyphicon-eye-open\n" +
                "glyphicon glyphicon-eye-close\n" +
                "glyphicon glyphicon-warning-sign\n" +
                "glyphicon glyphicon-plane\n" +
                "glyphicon glyphicon-calendar\n" +
                "glyphicon glyphicon-random\n" +
                "glyphicon glyphicon-comment\n" +
                "glyphicon glyphicon-magnet\n" +
                "glyphicon glyphicon-chevron-up\n" +
                "glyphicon glyphicon-chevron-down\n" +
                "glyphicon glyphicon-retweet\n" +
                "glyphicon glyphicon-shopping-cart\n" +
                "glyphicon glyphicon-folder-close\n" +
                "glyphicon glyphicon-folder-open\n" +
                "glyphicon glyphicon-resize-vertical\n" +
                "glyphicon glyphicon-resize-horizontal\n" +
                "glyphicon glyphicon-hdd\n" +
                "glyphicon glyphicon-bullhorn\n" +
                "glyphicon glyphicon-bell\n" +
                "glyphicon glyphicon-certificate\n" +
                "glyphicon glyphicon-thumbs-up\n" +
                "glyphicon glyphicon-thumbs-down\n" +
                "glyphicon glyphicon-hand-right\n" +
                "glyphicon glyphicon-hand-left\n" +
                "glyphicon glyphicon-hand-up\n" +
                "glyphicon glyphicon-hand-down\n" +
                "glyphicon glyphicon-circle-arrow-right\n" +
                "glyphicon glyphicon-circle-arrow-left\n" +
                "glyphicon glyphicon-circle-arrow-up\n" +
                "glyphicon glyphicon-circle-arrow-down\n" +
                "glyphicon glyphicon-globe\n" +
                "glyphicon glyphicon-wrench\n" +
                "glyphicon glyphicon-tasks\n" +
                "glyphicon glyphicon-filter\n" +
                "glyphicon glyphicon-briefcase\n" +
                "glyphicon glyphicon-fullscreen\n" +
                "glyphicon glyphicon-dashboard\n" +
                "glyphicon glyphicon-paperclip\n" +
                "glyphicon glyphicon-heart-empty\n" +
                "glyphicon glyphicon-link\n" +
                "glyphicon glyphicon-phone\n" +
                "glyphicon glyphicon-pushpin\n" +
                "glyphicon glyphicon-usd\n" +
                "glyphicon glyphicon-gbp\n" +
                "glyphicon glyphicon-sort\n" +
                "glyphicon glyphicon-sort-by-alphabet\n" +
                "glyphicon glyphicon-sort-by-alphabet-alt\n" +
                "glyphicon glyphicon-sort-by-order\n" +
                "glyphicon glyphicon-sort-by-order-alt\n" +
                "glyphicon glyphicon-sort-by-attributes\n" +
                "glyphicon glyphicon-sort-by-attributes-alt\n" +
                "glyphicon glyphicon-unchecked\n" +
                "glyphicon glyphicon-expand\n" +
                "glyphicon glyphicon-collapse-down\n" +
                "glyphicon glyphicon-collapse-up\n" +
                "glyphicon glyphicon-log-in\n" +
                "glyphicon glyphicon-flash\n" +
                "glyphicon glyphicon-log-out\n" +
                "glyphicon glyphicon-new-window\n" +
                "glyphicon glyphicon-record\n" +
                "glyphicon glyphicon-save\n" +
                "glyphicon glyphicon-open\n" +
                "glyphicon glyphicon-saved\n" +
                "glyphicon glyphicon-import\n" +
                "glyphicon glyphicon-export\n" +
                "glyphicon glyphicon-send\n" +
                "glyphicon glyphicon-floppy-disk\n" +
                "glyphicon glyphicon-floppy-saved\n" +
                "glyphicon glyphicon-floppy-remove\n" +
                "glyphicon glyphicon-floppy-save\n" +
                "glyphicon glyphicon-floppy-open\n" +
                "glyphicon glyphicon-credit-card\n" +
                "glyphicon glyphicon-transfer\n" +
                "glyphicon glyphicon-cutlery\n" +
                "glyphicon glyphicon-header\n" +
                "glyphicon glyphicon-compressed\n" +
                "glyphicon glyphicon-earphone\n" +
                "glyphicon glyphicon-phone-alt\n" +
                "glyphicon glyphicon-tower\n" +
                "glyphicon glyphicon-stats\n" +
                "glyphicon glyphicon-sd-video\n" +
                "glyphicon glyphicon-hd-video\n" +
                "glyphicon glyphicon-subtitles\n" +
                "glyphicon glyphicon-sound-stereo\n" +
                "glyphicon glyphicon-sound-dolby\n" +
                "glyphicon glyphicon-sound-5-1\n" +
                "glyphicon glyphicon-sound-6-1\n" +
                "glyphicon glyphicon-sound-7-1\n" +
                "glyphicon glyphicon-copyright-mark\n" +
                "glyphicon glyphicon-registration-mark\n" +
                "glyphicon glyphicon-cloud-download\n" +
                "glyphicon glyphicon-cloud-upload\n" +
                "glyphicon glyphicon-tree-conifer\n" +
                "glyphicon glyphicon-tree-deciduous\n" +
                "glyphicon glyphicon-cd\n" +
                "glyphicon glyphicon-save-file\n" +
                "glyphicon glyphicon-open-file\n" +
                "glyphicon glyphicon-level-up\n" +
                "glyphicon glyphicon-copy\n" +
                "glyphicon glyphicon-paste\n" +
                "glyphicon glyphicon-alert\n" +
                "glyphicon glyphicon-equalizer\n" +
                "glyphicon glyphicon-king\n" +
                "glyphicon glyphicon-queen\n" +
                "glyphicon glyphicon-pawn\n" +
                "glyphicon glyphicon-bishop\n" +
                "glyphicon glyphicon-knight\n" +
                "glyphicon glyphicon-baby-formula\n" +
                "glyphicon glyphicon-tent\n" +
                "glyphicon glyphicon-blackboard\n" +
                "glyphicon glyphicon-bed\n" +
                "glyphicon glyphicon-apple\n" +
                "glyphicon glyphicon-erase\n" +
                "glyphicon glyphicon-hourglass\n" +
                "glyphicon glyphicon-lamp\n" +
                "glyphicon glyphicon-duplicate\n" +
                "glyphicon glyphicon-piggy-bank\n" +
                "glyphicon glyphicon-scissors\n" +
                "glyphicon glyphicon-bitcoin\n" +
                "glyphicon glyphicon-btc\n" +
                "glyphicon glyphicon-xbt\n" +
                "glyphicon glyphicon-yen\n" +
                "glyphicon glyphicon-jpy\n" +
                "glyphicon glyphicon-ruble\n" +
                "glyphicon glyphicon-rub\n" +
                "glyphicon glyphicon-scale\n" +
                "glyphicon glyphicon-ice-lolly\n" +
                "glyphicon glyphicon-ice-lolly-tasted\n" +
                "glyphicon glyphicon-education\n" +
                "glyphicon glyphicon-option-horizontal\n" +
                "glyphicon glyphicon-option-vertical\n" +
                "glyphicon glyphicon-menu-hamburger\n" +
                "glyphicon glyphicon-modal-window\n" +
                "glyphicon glyphicon-oil\n" +
                "glyphicon glyphicon-grain\n" +
                "glyphicon glyphicon-sunglasses\n" +
                "glyphicon glyphicon-text-size\n" +
                "glyphicon glyphicon-text-color\n" +
                "glyphicon glyphicon-text-background\n" +
                "glyphicon glyphicon-object-align-top\n" +
                "glyphicon glyphicon-object-align-bottom\n" +
                "glyphicon glyphicon-object-align-horizontal\n" +
                "glyphicon glyphicon-object-align-left\n" +
                "glyphicon glyphicon-object-align-vertical\n" +
                "glyphicon glyphicon-object-align-right\n" +
                "glyphicon glyphicon-triangle-right\n" +
                "glyphicon glyphicon-triangle-left\n" +
                "glyphicon glyphicon-triangle-bottom\n" +
                "glyphicon glyphicon-triangle-top\n" +
                "glyphicon glyphicon-console\n" +
                "glyphicon glyphicon-superscript\n" +
                "glyphicon glyphicon-subscript\n" +
                "glyphicon glyphicon-menu-left\n" +
                "glyphicon glyphicon-menu-right\n" +
                "glyphicon glyphicon-menu-down\n" +
                "glyphicon glyphicon-menu-up";
    }

    @Test
    public void add(){
        IconDao dao = context.getBean(IconDao.class);

        String[] split = icon.split("\n");

        List<Icon> iconList = new ArrayList<>();
        for (String s : split){
            Icon icon1 = new Icon();
            icon1.setGuid(UUID.randomUUID().toString());
            icon1.setCssName(s);
            iconList.add(icon1);
        }

        dao.addBatch(iconList);
    }
}
