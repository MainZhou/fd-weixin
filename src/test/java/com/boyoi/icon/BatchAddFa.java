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
public class BatchAddFa {
    ApplicationContext context=null;
    String icon ;
    @Before
    public void init(){
        context = new ClassPathXmlApplicationContext(new String[]{"classpath*:/spring/spring_daos.xml"});
        icon = "fa fa-fw fa-adjust\n" +
                "fa fa-fw fa-anchor\n" +
                "fa fa-fw fa-archive\n" +
                "fa fa-fw fa-area-chart\n" +
                "fa fa-fw fa-arrows\n" +
                "fa fa-fw fa-arrows-h\n" +
                "fa fa-fw fa-arrows-v\n" +
                "fa fa-fw fa-asterisk\n" +
                "fa fa-fw fa-at\n" +
                "fa fa-fw fa-automobile\n" +
                "fa fa-fw fa-ban\n" +
                "fa fa-fw fa-bank\n" +
                "fa fa-fw fa-bar-chart\n" +
                "fa fa-fw fa-bar-chart-o\n" +
                "fa fa-fw fa-barcode\n" +
                "fa fa-fw fa-bars\n" +
                "fa fa-fw fa-bed\n" +
                "fa fa-fw fa-beer\n" +
                "fa fa-fw fa-bell\n" +
                "fa fa-fw fa-bell-o\n" +
                "fa fa-fw fa-bell-slash\n" +
                "fa fa-fw fa-bell-slash-o\n" +
                "fa fa-fw fa-bicycle\n" +
                "fa fa-fw fa-binoculars\n" +
                "fa fa-fw fa-birthday-cake\n" +
                "fa fa-fw fa-bolt\n" +
                "fa fa-fw fa-bomb\n" +
                "fa fa-fw fa-book\n" +
                "fa fa-fw fa-bookmark\n" +
                "fa fa-fw fa-bookmark-o\n" +
                "fa fa-fw fa-briefcase\n" +
                "fa fa-fw fa-bug\n" +
                "fa fa-fw fa-building\n" +
                "fa fa-fw fa-building-o\n" +
                "fa fa-fw fa-bullhorn\n" +
                "fa fa-fw fa-bullseye\n" +
                "fa fa-fw fa-bus\n" +
                "fa fa-fw fa-cab\n" +
                "fa fa-fw fa-calculator\n" +
                "fa fa-fw fa-calendar\n" +
                "fa fa-fw fa-calendar-o\n" +
                "fa fa-fw fa-camera\n" +
                "fa fa-fw fa-camera-retro\n" +
                "fa fa-fw fa-car\n" +
                "fa fa-fw fa-caret-square-o-down\n" +
                "fa fa-fw fa-caret-square-o-left\n" +
                "fa fa-fw fa-caret-square-o-right\n" +
                "fa fa-fw fa-caret-square-o-up\n" +
                "fa fa-fw fa-cart-arrow-down\n" +
                "fa fa-fw fa-cart-plus\n" +
                "fa fa-fw fa-cc\n" +
                "fa fa-fw fa-certificate\n" +
                "fa fa-fw fa-check\n" +
                "fa fa-fw fa-check-circle\n" +
                "fa fa-fw fa-check-circle-o\n" +
                "fa fa-fw fa-check-square\n" +
                "fa fa-fw fa-check-square-o\n" +
                "fa fa-fw fa-child\n" +
                "fa fa-fw fa-circle\n" +
                "fa fa-fw fa-circle-o\n" +
                "fa fa-fw fa-circle-o-notch\n" +
                "fa fa-fw fa-circle-thin\n" +
                "fa fa-fw fa-clock-o\n" +
                "fa fa-fw fa-close\n" +
                "fa fa-fw fa-cloud\n" +
                "fa fa-fw fa-cloud-download\n" +
                "fa fa-fw fa-cloud-upload\n" +
                "fa fa-fw fa-code\n" +
                "fa fa-fw fa-code-fork\n" +
                "fa fa-fw fa-coffee\n" +
                "fa fa-fw fa-cog\n" +
                "fa fa-fw fa-cogs\n" +
                "fa fa-fw fa-comment\n" +
                "fa fa-fw fa-comment-o\n" +
                "fa fa-fw fa-comments\n" +
                "fa fa-fw fa-comments-o\n" +
                "fa fa-fw fa-compass\n" +
                "fa fa-fw fa-copyright\n" +
                "fa fa-fw fa-credit-card\n" +
                "fa fa-fw fa-crop\n" +
                "fa fa-fw fa-crosshairs\n" +
                "fa fa-fw fa-cube\n" +
                "fa fa-fw fa-cubes\n" +
                "fa fa-fw fa-cutlery\n" +
                "fa fa-fw fa-dashboard\n" +
                "fa fa-fw fa-database\n" +
                "fa fa-fw fa-desktop\n" +
                "fa fa-fw fa-diamond\n" +
                "fa fa-fw fa-dot-circle-o\n" +
                "fa fa-fw fa-download\n" +
                "fa fa-fw fa-edit\n" +
                "fa fa-fw fa-ellipsis-h\n" +
                "fa fa-fw fa-ellipsis-v\n" +
                "fa fa-fw fa-envelope\n" +
                "fa fa-fw fa-envelope-o\n" +
                "fa fa-fw fa-envelope-square\n" +
                "fa fa-fw fa-eraser\n" +
                "fa fa-fw fa-exchange\n" +
                "fa fa-fw fa-exclamation\n" +
                "fa fa-fw fa-exclamation-circle\n" +
                "fa fa-fw fa-exclamation-triangle\n" +
                "fa fa-fw fa-external-link\n" +
                "fa fa-fw fa-external-link-square\n" +
                "fa fa-fw fa-eye\n" +
                "fa fa-fw fa-eye-slash\n" +
                "fa fa-fw fa-eyedropper\n" +
                "fa fa-fw fa-fax\n" +
                "fa fa-fw fa-female\n" +
                "fa fa-fw fa-fighter-jet\n" +
                "fa fa-fw fa-file-archive-o\n" +
                "fa fa-fw fa-file-audio-o\n" +
                "fa fa-fw fa-file-code-o\n" +
                "fa fa-fw fa-file-excel-o\n" +
                "fa fa-fw fa-file-image-o\n" +
                "fa fa-fw fa-file-movie-o\n" +
                "fa fa-fw fa-file-pdf-o\n" +
                "fa fa-fw fa-file-photo-o\n" +
                "fa fa-fw fa-file-picture-o\n" +
                "fa fa-fw fa-file-powerpoint-o\n" +
                "fa fa-fw fa-file-sound-o\n" +
                "fa fa-fw fa-file-video-o\n" +
                "fa fa-fw fa-file-word-o\n" +
                "fa fa-fw fa-file-zip-o\n" +
                "fa fa-fw fa-film\n" +
                "fa fa-fw fa-filter\n" +
                "fa fa-fw fa-fire\n" +
                "fa fa-fw fa-fire-extinguisher\n" +
                "fa fa-fw fa-flag\n" +
                "fa fa-fw fa-flag-checkered\n" +
                "fa fa-fw fa-flag-o\n" +
                "fa fa-fw fa-flash\n" +
                "fa fa-fw fa-flask\n" +
                "fa fa-fw fa-folder\n" +
                "fa fa-fw fa-folder-o\n" +
                "fa fa-fw fa-folder-open\n" +
                "fa fa-fw fa-folder-open-o\n" +
                "fa fa-fw fa-frown-o\n" +
                "fa fa-fw fa-futbol-o\n" +
                "fa fa-fw fa-gamepad\n" +
                "fa fa-fw fa-gavel\n" +
                "fa fa-fw fa-gear\n" +
                "fa fa-fw fa-gears\n" +
                "fa fa-fw fa-genderless\n" +
                "fa fa-fw fa-gift\n" +
                "fa fa-fw fa-glass\n" +
                "fa fa-fw fa-globe\n" +
                "fa fa-fw fa-graduation-cap\n" +
                "fa fa-fw fa-group\n" +
                "fa fa-fw fa-hdd-o\n" +
                "fa fa-fw fa-headphones\n" +
                "fa fa-fw fa-heart\n" +
                "fa fa-fw fa-heart-o\n" +
                "fa fa-fw fa-heartbeat\n" +
                "fa fa-fw fa-history\n" +
                "fa fa-fw fa-home\n" +
                "fa fa-fw fa-hotel\n" +
                "fa fa-fw fa-image\n" +
                "fa fa-fw fa-inbox\n" +
                "fa fa-fw fa-info\n" +
                "fa fa-fw fa-info-circle\n" +
                "fa fa-fw fa-institution\n" +
                "fa fa-fw fa-key\n" +
                "fa fa-fw fa-keyboard-o\n" +
                "fa fa-fw fa-language\n" +
                "fa fa-fw fa-laptop\n" +
                "fa fa-fw fa-leaf\n" +
                "fa fa-fw fa-legal\n" +
                "fa fa-fw fa-lemon-o\n" +
                "fa fa-fw fa-level-down\n" +
                "fa fa-fw fa-level-up\n" +
                "fa fa-fw fa-life-bouy\n" +
                "fa fa-fw fa-life-buoy\n" +
                "fa fa-fw fa-life-ring\n" +
                "fa fa-fw fa-life-saver\n" +
                "fa fa-fw fa-lightbulb-o\n" +
                "fa fa-fw fa-line-chart\n" +
                "fa fa-fw fa-location-arrow\n" +
                "fa fa-fw fa-lock\n" +
                "fa fa-fw fa-magic\n" +
                "fa fa-fw fa-magnet\n" +
                "fa fa-fw fa-mail-forward\n" +
                "fa fa-fw fa-mail-reply\n" +
                "fa fa-fw fa-mail-reply-all\n" +
                "fa fa-fw fa-male\n" +
                "fa fa-fw fa-map-marker\n" +
                "fa fa-fw fa-meh-o\n" +
                "fa fa-fw fa-microphone\n" +
                "fa fa-fw fa-microphone-slash\n" +
                "fa fa-fw fa-minus\n" +
                "fa fa-fw fa-minus-circle\n" +
                "fa fa-fw fa-minus-square\n" +
                "fa fa-fw fa-minus-square-o\n" +
                "fa fa-fw fa-mobile\n" +
                "fa fa-fw fa-mobile-phone\n" +
                "fa fa-fw fa-money\n" +
                "fa fa-fw fa-moon-o\n" +
                "fa fa-fw fa-mortar-board\n" +
                "fa fa-fw fa-motorcycle\n" +
                "fa fa-fw fa-music\n" +
                "fa fa-fw fa-navicon\n" +
                "fa fa-fw fa-newspaper-o\n" +
                "fa fa-fw fa-paint-brush\n" +
                "fa fa-fw fa-paper-plane\n" +
                "fa fa-fw fa-paper-plane-o\n" +
                "fa fa-fw fa-paw\n" +
                "fa fa-fw fa-pencil\n" +
                "fa fa-fw fa-pencil-square\n" +
                "fa fa-fw fa-pencil-square-o\n" +
                "fa fa-fw fa-phone\n" +
                "fa fa-fw fa-phone-square\n" +
                "fa fa-fw fa-photo\n" +
                "fa fa-fw fa-picture-o\n" +
                "fa fa-fw fa-pie-chart\n" +
                "fa fa-fw fa-plane\n" +
                "fa fa-fw fa-plug\n" +
                "fa fa-fw fa-plus\n" +
                "fa fa-fw fa-plus-circle\n" +
                "fa fa-fw fa-plus-square\n" +
                "fa fa-fw fa-plus-square-o\n" +
                "fa fa-fw fa-power-off\n" +
                "fa fa-fw fa-print\n" +
                "fa fa-fw fa-puzzle-piece\n" +
                "fa fa-fw fa-qrcode\n" +
                "fa fa-fw fa-question\n" +
                "fa fa-fw fa-question-circle\n" +
                "fa fa-fw fa-quote-left\n" +
                "fa fa-fw fa-quote-right\n" +
                "fa fa-fw fa-random\n" +
                "fa fa-fw fa-recycle\n" +
                "fa fa-fw fa-refresh\n" +
                "fa fa-fw fa-remove\n" +
                "fa fa-fw fa-reorder\n" +
                "fa fa-fw fa-reply\n" +
                "fa fa-fw fa-reply-all\n" +
                "fa fa-fw fa-retweet\n" +
                "fa fa-fw fa-road\n" +
                "fa fa-fw fa-rocket\n" +
                "fa fa-fw fa-rss\n" +
                "fa fa-fw fa-rss-square\n" +
                "fa fa-fw fa-search\n" +
                "fa fa-fw fa-search-minus\n" +
                "fa fa-fw fa-search-plus\n" +
                "fa fa-fw fa-send\n" +
                "fa fa-fw fa-send-o\n" +
                "fa fa-fw fa-server\n" +
                "fa fa-fw fa-share\n" +
                "fa fa-fw fa-share-alt\n" +
                "fa fa-fw fa-share-alt-square\n" +
                "fa fa-fw fa-share-square\n" +
                "fa fa-fw fa-share-square-o\n" +
                "fa fa-fw fa-shield\n" +
                "fa fa-fw fa-ship\n" +
                "fa fa-fw fa-shopping-cart\n" +
                "fa fa-fw fa-sign-in\n" +
                "fa fa-fw fa-sign-out\n" +
                "fa fa-fw fa-signal\n" +
                "fa fa-fw fa-sitemap\n" +
                "fa fa-fw fa-sliders\n" +
                "fa fa-fw fa-smile-o\n" +
                "fa fa-fw fa-soccer-ball-o\n" +
                "fa fa-fw fa-sort\n" +
                "fa fa-fw fa-sort-alpha-asc\n" +
                "fa fa-fw fa-sort-alpha-desc\n" +
                "fa fa-fw fa-sort-amount-asc\n" +
                "fa fa-fw fa-sort-amount-desc\n" +
                "fa fa-fw fa-sort-asc\n" +
                "fa fa-fw fa-sort-desc\n" +
                "fa fa-fw fa-sort-down\n" +
                "fa fa-fw fa-sort-numeric-asc\n" +
                "fa fa-fw fa-sort-numeric-desc\n" +
                "fa fa-fw fa-sort-up\n" +
                "fa fa-fw fa-space-shuttle\n" +
                "fa fa-fw fa-spinner\n" +
                "fa fa-fw fa-spoon\n" +
                "fa fa-fw fa-square\n" +
                "fa fa-fw fa-square-o\n" +
                "fa fa-fw fa-star\n" +
                "fa fa-fw fa-star-half\n" +
                "fa fa-fw fa-star-half-empty\n" +
                "fa fa-fw fa-star-half-full\n" +
                "fa fa-fw fa-star-half-o\n" +
                "fa fa-fw fa-star-o\n" +
                "fa fa-fw fa-street-view\n" +
                "fa fa-fw fa-suitcase\n" +
                "fa fa-fw fa-sun-o\n" +
                "fa fa-fw fa-support\n" +
                "fa fa-fw fa-tablet\n" +
                "fa fa-fw fa-tachometer\n" +
                "fa fa-fw fa-tag\n" +
                "fa fa-fw fa-tags\n" +
                "fa fa-fw fa-tasks\n" +
                "fa fa-fw fa-taxi\n" +
                "fa fa-fw fa-terminal\n" +
                "fa fa-fw fa-thumb-tack\n" +
                "fa fa-fw fa-thumbs-down\n" +
                "fa fa-fw fa-thumbs-o-down\n" +
                "fa fa-fw fa-thumbs-o-up\n" +
                "fa fa-fw fa-thumbs-up\n" +
                "fa fa-fw fa-ticket\n" +
                "fa fa-fw fa-times\n" +
                "fa fa-fw fa-times-circle\n" +
                "fa fa-fw fa-times-circle-o\n" +
                "fa fa-fw fa-tint\n" +
                "fa fa-fw fa-toggle-down\n" +
                "fa fa-fw fa-toggle-left\n" +
                "fa fa-fw fa-toggle-off\n" +
                "fa fa-fw fa-toggle-on\n" +
                "fa fa-fw fa-toggle-right\n" +
                "fa fa-fw fa-toggle-up\n" +
                "fa fa-fw fa-trash\n" +
                "fa fa-fw fa-trash-o\n" +
                "fa fa-fw fa-tree\n" +
                "fa fa-fw fa-trophy\n" +
                "fa fa-fw fa-truck\n" +
                "fa fa-fw fa-tty\n" +
                "fa fa-fw fa-umbrella\n" +
                "fa fa-fw fa-university\n" +
                "fa fa-fw fa-unlock\n" +
                "fa fa-fw fa-unlock-alt\n" +
                "fa fa-fw fa-unsorted\n" +
                "fa fa-fw fa-upload\n" +
                "fa fa-fw fa-user\n" +
                "fa fa-fw fa-user-plus\n" +
                "fa fa-fw fa-user-secret\n" +
                "fa fa-fw fa-user-times\n" +
                "fa fa-fw fa-users\n" +
                "fa fa-fw fa-video-camera\n" +
                "fa fa-fw fa-volume-down\n" +
                "fa fa-fw fa-volume-off\n" +
                "fa fa-fw fa-volume-up\n" +
                "fa fa-fw fa-warning\n" +
                "fa fa-fw fa-wheelchair\n" +
                "fa fa-fw fa-wifi\n" +
                "fa fa-fw fa-wrench";
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
