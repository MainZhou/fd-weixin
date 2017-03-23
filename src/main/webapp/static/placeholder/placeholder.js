/**
 * @file 为不支持placeholder的浏览器实现占位符功能
 * @author lizhaoxia(lizhaoxia@baidu.com)
 * @date 2013-10-29
 */

define( function ( require ) {
    /**
     * 判断是否为IE浏览器，若是则返回版本号，否则返回undefined
     * 
     * @return {number} IE版本号
     */
    function isIE() {
        return /msie (\d+\.\d+)/i.test(navigator.userAgent)
            ? (document.documentMode || + RegExp['\x241']) : undefined;
    }

    /**
     * 检测当前浏览器是否支持placeholder属性
     * 
     * @private
     * @return {boolean} 
     */
    function supportPlaceholder() {
        return 'placeholder' in document.createElement('input');
    }

    /**
     * 获取ele节点的placeholder属性值，修正ie7下不能取到placeholder属性的问题
     * 
     * @private
     * @param {HTMLElement} ele 目标dom节点
     * @return {string} ele节点的placeholder属性值，若无，则返回空串
     */
    function getPlaceholderAttr(ele) {
        var placeholder = ele.getAttribute('placeholder');

        // 修正ie7下不能取到placeholder属性的问题
        if (isIE() === 7 && placeholder === null 
            && /placeholder=/.test(ele.outerHTML)
        ) { 
            var outer = ele.outerHTML;

            placeholder = /placeholder=\"/.test(outer)
                ? outer.match(/placeholder=\"[^\"]*/)[0].substr(13)
                : outer.match(/placeholder=[^\s>]*/)[0].substr(12);
        }

        return placeholder ? placeholder : '';
    }

    /**
     * 将目标元素添加到基准元素之前
     * 
     * @param {HTMLElement} newElement 基准元素
     * @param {HTMLElement} existElement 目标元素
     * @return {HTMLElement} 被添加的目标元素
     */
    function insertBefore(newElement, existElement) {
        var existParent = existElement.parentNode;

        if (existParent) {
            existParent.insertBefore(newElement, existElement);
        }

        return newElement;
    }

    /**
     * 去掉目标字符串两端的空白字符
     * 
     * @param {string} str 目标字符串
     * @return {string} 去掉两端空白后的字符串
     */
    function trim(str) {
        var trimer = new RegExp(
            "(^[\\s\\t\\xa0\\u3000]+)|([\\u3000\\xa0\\s\\t]+\x24)",
            "g"
        );

        return String(str).replace(trimer, "");
    }

    /**
     * 为元素绑定事件监听
     * 
     * @param {HTMLElement|string} element 目标节点
     * @param {string} type 事件类型
     * @param {Function} listener 监听器
     * @return {HTMLElement} 目标节点对象
     */
    function on(element, type, listener) {
        var element = typeof element === 'string'
            ? document.getElementById(element): element;
        if (element.addEventListener) {
            element.addEventListener(type, listener, false);
        }
        else if (element.attachEvent) {
            element.attachEvent('on' + type, listener);
        }

        return element;
    }

    /**
     * 为ele元素设置占位符
     * 
     * @private
     * @param {HTMLElement} ele 需要设置占位符的input或者textarea节点
     */
    function setPlaceholder(ele) {
        var placeholder = getPlaceholderAttr(ele);
        if (placeholder.length > 0) {
            // 若已有一个label，则先remove
            var prevLabel = ele.previousSibling;
            if (prevLabel
                && prevLabel.nodeType === 1
                && prevLabel.tagName.toLowerCase() === 'label'
                && prevLabel.className === 'placeholder-label'
            ) {
                prevLabel.parentNode && prevLabel.parentNode.removeChild(prevLabel);
            }

            // 生成并插入label节点
            var labelEle = document.createElement('label');
            var eleStyle = ele.currentStyle;

            labelEle.style.position = 'absolute';
            labelEle.style.color = ele.getAttribute('placeholder-color') || '#a9a9a9';
            labelEle.style.cursor = 'text';
            labelEle.style.fontSize = eleStyle.fontSize;
            labelEle.style.fontFamily = eleStyle.fontFamily;
            labelEle.style.textAlign = eleStyle.textAlign;
            labelEle.style.marginLeft = eleStyle.marginLeft === 'auto' ? '0' : eleStyle.marginLeft;
            labelEle.style.marginTop = eleStyle.marginTop === 'auto' ? '0' : eleStyle.marginTop;
            labelEle.style.marginRight = eleStyle.marginRight === 'auto' ? '0' : eleStyle.marginRight;
            labelEle.style.marginBottom = eleStyle.marginBottom === 'auto' ? '0' : eleStyle.marginBottom;

            labelEle.style.width = eleStyle.width === 'auto'
                ? ele.offsetWidth - parseInt(eleStyle.paddingLeft)
                    - parseInt(eleStyle.paddingRight) + 'px'
                : eleStyle.width;

            if (eleStyle.position === 'relative') {
                labelEle.style.zIndex = eleStyle.zIndex + 1;
                labelEle.style.marginTop = parseInt(eleStyle.top) + parseInt(eleStyle.marginTop) + 'px';
                labelEle.style.marginLeft = parseInt(eleStyle.left) + parseInt(eleStyle.marginLeft) + 'px';
            }

            if (eleStyle.position === 'absolute') {
                labelEle.style.zIndex = eleStyle.zIndex === 'auto' ? 1 : eleStyle.zIndex + 1;
                labelEle.style.top = eleStyle.top;
                labelEle.style.right = eleStyle.right;
                labelEle.style.bottom = eleStyle.bottom;
                labelEle.style.left = eleStyle.left;
            }

            // 若ele目标节点为input输入框，则将label文字居中，超出部分文字隐藏
            if (ele.tagName.toLowerCase() === 'input') {
                labelEle.style.display = 'inline-block';
                labelEle.style.height = ele.offsetHeight + 'px';
                labelEle.style.paddingRight = eleStyle.paddingRight;
                labelEle.style.paddingLeft = eleStyle.paddingLeft;
                labelEle.style.lineHeight = labelEle.style.height;
                labelEle.style.overflow = 'hidden';
            }
            // ele目标节点为textarea，默认部分可滚动
            else {
                labelEle.style.height = eleStyle.height === 'auto'
                    ? ele.offsetHeight - parseInt(eleStyle.paddingTop)
                        - parseInt(eleStyle.paddingBottom) + 'px'
                    : eleStyle.height;
                labelEle.style.padding = eleStyle.padding;
                labelEle.style.overflowY = 'scroll';

                // 修正1-2px的偏移
                var offset = isIE() > 7 ? 1 : 2;
                labelEle.style.marginTop = parseInt(labelEle.style.marginTop) + offset + 'px';
            }

            // 为label添加class，供自定义使用
            labelEle.className = 'placeholder-label';

            labelEle.innerHTML = trim(ele.value) ? '' : placeholder;
            insertBefore(labelEle, ele);

            // 绑定事件
            on(labelEle, 'click', function() {
                ele.focus();
            });

            on(ele, 'focus', function() {
                labelEle.innerHTML = '';
            });

            on(ele, 'blur', function() {
                if (ele.value === '') {
                    labelEle.innerHTML = placeholder;
                }
                else {
                    labelEle.innerHTML = '';
                }
            });
        }
    }

    /**
     * 获取页面中所有的input(text类型)和textarea节点
     * 
     * @return {array} 
     */
    function getAllInputAndTextarea() {
        var inputs = document.getElementsByTagName('input');
        var textareas = document.getElementsByTagName('textarea');
        var results = [];

        for (var i = inputs.length - 1; i >= 0; i--) {
            if (inputs[i].type === 'text' || inputs[i].type === 'password')
                results.push(inputs[i]);
        };
        
        for (var j = textareas.length - 1; j >= 0; j--) {
            results.push(textareas[j]);
        };

        return results;
    }

    /**
     * 占位符初始化
     * 
     * @public
     * @param {array[HTMLElement]} elements 需要初始化占位符的input或textarea对象数组
     */
    function init(elements) {
        var elements = elements || getAllInputAndTextarea();
        if (elements && !supportPlaceholder()) {
            for (var i = elements.length - 1; i >= 0; i--) {
                setPlaceholder(elements[i]);
            };
        }
    }

    // return模块
    return {
        init: init
    };
} );