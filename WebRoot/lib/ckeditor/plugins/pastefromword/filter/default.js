﻿/*
 Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 For licensing, see LICENSE.md or http://ckeditor.com/license
*/
(function() {
    function C(a) {
        a = a.toUpperCase();
        for (var c = D.length,
        b = 0,
        f = 0; f < c; ++f) for (var d = D[f], e = d[1].length; a.substr(0, e) == d[1]; a = a.substr(e)) b += d[0];
        return b
    }
    function E(a) {
        a = a.toUpperCase();
        for (var c = 1,
        b = 1; 0 < a.length; b *= 26) c += "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(a.charAt(a.length - 1)) * b,
        a = a.substr(0, a.length - 1);
        return c
    }
    var F = CKEDITOR.htmlParser.fragment.prototype,
    r = CKEDITOR.htmlParser.element.prototype;
    F.onlyChild = r.onlyChild = function() {
        var a = this.children;
        return 1 == a.length && a[0] || null
    };
    r.removeAnyChildWithName = function(a) {
        for (var c = this.children,
        b = [], f, d = 0; d < c.length; d++) f = c[d],
        f.name && (f.name == a && (b.push(f), c.splice(d--, 1)), b = b.concat(f.removeAnyChildWithName(a)));
        return b
    };
    r.getAncestor = function(a) {
        for (var c = this.parent; c && (!c.name || !c.name.match(a));) c = c.parent;
        return c
    };
    F.firstChild = r.firstChild = function(a) {
        for (var c, b = 0; b < this.children.length; b++) if (c = this.children[b], a(c) || c.name && (c = c.firstChild(a))) return c;
        return null
    };
    r.addStyle = function(a, c, b) {
        var f = "";
        if ("string" == typeof c) f += a + ":" + c + ";";
        else {
            if ("object" == typeof a) for (var d in a) a.hasOwnProperty(d) && (f += d + ":" + a[d] + ";");
            else f += a;
            b = c
        }
        this.attributes || (this.attributes = {});
        a = this.attributes.style || "";
        a = (b ? [f, a] : [a, f]).join(";");
        this.attributes.style = a.replace(/^;+|;(?=;)/g, "")
    };
    r.getStyle = function(a) {
        var c = this.attributes.style;
        if (c) return c = CKEDITOR.tools.parseCssText(c, 1),
        c[a]
    };
    CKEDITOR.dtd.parentOf = function(a) {
        var c = {},
        b;
        for (b in this) - 1 == b.indexOf("$") && this[b][a] && (c[b] = 1);
        return c
    };
    var G = /^(?:\b0[^\s]*\s*){1,4}$/,
    B = {
        ol: {
            decimal: /\d+/,
            "lower-roman": /^m{0,4}(cm|cd|d?c{0,3})(xc|xl|l?x{0,3})(ix|iv|v?i{0,3})$/,
            "upper-roman": /^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$/,
            "lower-alpha": /^[a-z]+$/,
            "upper-alpha": /^[A-Z]+$/
        },
        ul: {
            disc: /[l\u00B7\u2002]/,
            circle: /[\u006F\u00D8]/,
            square: /[\u006E\u25C6]/
        }
    },
    D = [[1E3, "M"], [900, "CM"], [500, "D"], [400, "CD"], [100, "C"], [90, "XC"], [50, "L"], [40, "XL"], [10, "X"], [9, "IX"], [5, "V"], [4, "IV"], [1, "I"]],
    w = 0,
    x = null,
    A,
    H = CKEDITOR.plugins.pastefromword = {
        utils: {
            createListBulletMarker: function(a, c) {
                var b = new CKEDITOR.htmlParser.element("cke:listbullet");
                b.attributes = {
                    "cke:listsymbol": a[0]
                };
                b.add(new CKEDITOR.htmlParser.text(c));
                return b
            },
            isListBulletIndicator: function(a) {
                if (/mso-list\s*:\s*Ignore/i.test(a.attributes && a.attributes.style)) return ! 0
            },
            isContainingOnlySpaces: function(a) {
                var c;
                return (c = a.onlyChild()) && /^(:?\s|&nbsp;)+$/.test(c.value)
            },
            resolveList: function(a) {
                var c = a.attributes,
                b;
                if ((b = a.removeAnyChildWithName("cke:listbullet")) && b.length && (b = b[0])) return a.name = "cke:li",
                c.style && (c.style = H.filters.stylesFilter([["text-indent"], ["line-height"], [/^margin(:?-left)?$/, null,
                function(a) {
                    a = a.split(" ");
                    a = CKEDITOR.tools.convertToPx(a[3] || a[1] || a[0]); ! w && null !== x && a > x && (w = a - x);
                    x = a;
                    c["cke:indent"] = w && Math.ceil(a / w) + 1 || 1
                }], [/^mso-list$/, null,
                function(a) {
                    a = a.split(" ");
                    if (! (2 > a.length)) {
                        var b = Number(a[0].match(/\d+/));
                        a = Number(a[1].match(/\d+/));
                        1 == a && (b !== A && (c["cke:reset"] = 1), A = b);
                        c["cke:indent"] = a
                    }
                }]])(c.style, a) || ""),
                c["cke:indent"] || (x = 0, c["cke:indent"] = 1),
                CKEDITOR.tools.extend(c, b.attributes),
                !0;
                A = x = w = null;
                return ! 1
            },
            getStyleComponents: function() {
                var a = CKEDITOR.dom.element.createFromHtml('\x3cdiv style\x3d"position:absolute;left:-9999px;top:-9999px;"\x3e\x3c/div\x3e', CKEDITOR.document);
                CKEDITOR.document.getBody().append(a);
                return function(c, b, f) {
                    a.setStyle(c, b);
                    c = {};
                    b = f.length;
                    for (var d = 0; d < b; d++) c[f[d]] = a.getStyle(f[d]);
                    return c
                }
            } (),
            listDtdParents: CKEDITOR.dtd.parentOf("ol")
        },
        filters: {
            flattenList: function(a, c) {
                c = "number" == typeof c ? c: 1;
                var b = a.attributes,
                f;
                switch (b.type) {
                case "a":
                    f = "lower-alpha";
                    break;
                case "1":
                    f = "decimal"
                }
                for (var d = a.children,
                e, k = 0; k < d.length; k++) if (e = d[k], e.name in CKEDITOR.dtd.$listItem) {
                    var l = e.attributes,
                    g = e.children,
                    n = g[0],
                    h = g[g.length - 1];
                    n.attributes && n.attributes.style && -1 < n.attributes.style.indexOf("mso-list") && (e.attributes.style = n.attributes.style, n.replaceWithChildren());
                    h.name in CKEDITOR.dtd.$list && (a.add(h, k + 1), --g.length || d.splice(k--, 1));
                    e.name = "cke:li";
                    b.start && !k && (l.value = b.start);
                    H.filters.stylesFilter([["tab-stops", null,
                    function(a) { (a = a.match(/0$|\d+\.?\d*\w+/)) && (x = CKEDITOR.tools.convertToPx(a[0]))
                    }], 1 == c ? ["mso-list", null,
                    function(a) {
                        a = a.split(" ");
                        a = Number(a[0].match(/\d+/));
                        a !== A && (l["cke:reset"] = 1);
                        A = a
                    }] : null])(l.style);
                    l["cke:indent"] = c;
                    l["cke:listtype"] = a.name;
                    l["cke:list-style-type"] = f
                } else if (e.name in CKEDITOR.dtd.$list) {
                    arguments.callee.apply(this, [e, c + 1]);
                    d = d.slice(0, k).concat(e.children).concat(d.slice(k + 1));
                    a.children = [];
                    e = 0;
                    for (g = d.length; e < g; e++) a.add(d[e]);
                    d = a.children
                }
                delete a.name;
                b["cke:list"] = 1
            },
            assembleList: function(a) {
                var c = a.children,
                b, f, d, e, k, l;
                a = [];
                for (var g, n, h, p, m, t, q = 0; q < c.length; q++) if (b = c[q], "cke:li" == b.name) if (b.name = "li", f = b.attributes, h = (h = f["cke:listsymbol"]) && h.match(/^(?:[(]?)([^\s]+?)([.)]?)$/), p = m = t = null, f["cke:ignored"]) c.splice(q--, 1);
                else {
                    f["cke:reset"] && (l = e = k = null);
                    d = Number(f["cke:indent"]);
                    d != e && (n = g = null);
                    if (h) {
                        if (n && B[n][g].test(h[1])) p = n,
                        m = g;
                        else for (var u in B) for (var y in B[u]) if (B[u][y].test(h[1])) if ("ol" == u && /alpha|roman/.test(y)) {
                            if (g = /roman/.test(y) ? C(h[1]) : E(h[1]), !t || g < t) t = g,
                            p = u,
                            m = y
                        } else {
                            p = u;
                            m = y;
                            break
                        } ! p && (p = h[2] ? "ol": "ul")
                    } else p = f["cke:listtype"] || "ol",
                    m = f["cke:list-style-type"];
                    n = p;
                    g = m || ("ol" == p ? "decimal": "disc");
                    m && m != ("ol" == p ? "decimal": "disc") && b.addStyle("list-style-type", m);
                    if ("ol" == p && h) {
                        switch (m) {
                        case "decimal":
                            t = Number(h[1]);
                            break;
                        case "lower-roman":
                        case "upper-roman":
                            t = C(h[1]);
                            break;
                        case "lower-alpha":
                        case "upper-alpha":
                            t = E(h[1])
                        }
                        b.attributes.value = t
                    }
                    if (l) {
                        if (d > e) a.push(l = new CKEDITOR.htmlParser.element(p)),
                        l.add(b),
                        k.add(l);
                        else {
                            if (d < e) {
                                e -= d;
                                for (var v; e--&&(v = l.parent);) l = v.parent
                            }
                            l.add(b)
                        }
                        c.splice(q--, 1)
                    } else a.push(l = new CKEDITOR.htmlParser.element(p)),
                    l.add(b),
                    c[q] = l;
                    k = b;
                    e = d
                } else l && (l = e = k = null);
                for (q = 0; q < a.length; q++) if (l = a[q], u = l.children, g = g = void 0, y = l.children.length, v = g = void 0, c = /list-style-type:(.*?)(?:;|$)/, e = CKEDITOR.plugins.pastefromword.filters.stylesFilter, g = l.attributes, !c.exec(g.style)) {
                    for (k = 0; k < y; k++) if (g = u[k], g.attributes.value && Number(g.attributes.value) == k + 1 && delete g.attributes.value, g = c.exec(g.attributes.style)) if (g[1] != v && v) {
                        v = null;
                        break
                    } else v = g[1];
                    if (v) {
                        for (k = 0; k < y; k++) g = u[k].attributes,
                        g.style && (g.style = e([["list-style-type"]])(g.style) || "");
                        l.addStyle("list-style-type", v)
                    }
                }
                A = x = w = null
            },
            falsyFilter: function() {
                return ! 1
            },
            stylesFilter: function(a, c) {
                return function(b, f) {
                    var d = []; (b || "").replace(/&quot;/g, '"').replace(/\s*([^ :;]+)\s*:\s*([^;]+)\s*(?=;|$)/g,
                    function(b, e, g) {
                        e = e.toLowerCase();
                        "font-family" == e && (g = g.replace(/["']/g, ""));
                        for (var n, h, p, m = 0; m < a.length; m++) if (a[m] && (b = a[m][0], n = a[m][1], h = a[m][2], p = a[m][3], e.match(b) && (!n || g.match(n)))) {
                            e = p || e;
                            c && (h = h || g);
                            "function" == typeof h && (h = h(g, f, e));
                            h && h.push && (e = h[0], h = h[1]);
                            "string" == typeof h && d.push([e, h]);
                            return
                        } ! c && d.push([e, g])
                    });
                    for (var e = 0; e < d.length; e++) d[e] = d[e].join(":");
                    return d.length ? d.join(";") + ";": !1
                }
            },
            elementMigrateFilter: function(a, c) {
                return a ?
                function(b) {
                    var f = c ? (new CKEDITOR.style(a, c))._.definition: a;
                    b.name = f.element;
                    CKEDITOR.tools.extend(b.attributes, CKEDITOR.tools.clone(f.attributes));
                    b.addStyle(CKEDITOR.style.getStyleText(f));
                    f.attributes && f.attributes["class"] && (b.classWhiteList = " " + f.attributes["class"] + " ")
                }: function() {}
            },
            styleMigrateFilter: function(a, c) {
                var b = this.elementMigrateFilter;
                return a ?
                function(f, d) {
                    var e = new CKEDITOR.htmlParser.element(null),
                    k = {};
                    k[c] = f;
                    b(a, k)(e);
                    e.children = d.children;
                    d.children = [e];
                    e.filter = function() {};
                    e.parent = d
                }: function() {}
            },
            bogusAttrFilter: function(a, c) {
                if ( - 1 == c.name.indexOf("cke:")) return ! 1
            },
            applyStyleFilter: null
        },
        getRules: function(a, c) {
            var b = CKEDITOR.dtd,
            f = CKEDITOR.tools.extend({},
            b.$block, b.$listItem, b.$tableContent),
            d = a.config,
            e = this.filters,
            k = e.falsyFilter,
            l = e.stylesFilter,
            g = e.elementMigrateFilter,
            n = CKEDITOR.tools.bind(this.filters.styleMigrateFilter, this.filters),
            h = this.utils.createListBulletMarker,
            p = e.flattenList,
            m = e.assembleList,
            t = this.utils.isListBulletIndicator,
            q = this.utils.isContainingOnlySpaces,
            u = this.utils.resolveList,
            y = function(a) {
                a = CKEDITOR.tools.convertToPx(a);
                return isNaN(a) ? a: a + "px"
            },
            v = this.utils.getStyleComponents,
            x = this.utils.listDtdParents,
            r = !1 !== d.pasteFromWordRemoveFontStyles,
            w = !1 !== d.pasteFromWordRemoveStyles;
            return {
                elementNames: [[/meta|link|script/, ""]],
                root: function(a) {
                    a.filterChildren(c);
                    m(a)
                },
                elements: {
                    "^": function(a) {
                        var c;
                        CKEDITOR.env.gecko && (c = e.applyStyleFilter) && c(a)
                    },
                    $: function(a) {
                        var z = a.name || "",
                        e = a.attributes;
                        z in f && e.style && (e.style = l([[/^(:?width|height)$/, null, y]])(e.style) || "");
                        if (z.match(/h\d/)) {
                            a.filterChildren(c);
                            if (u(a)) return;
                            g(d["format_" + z])(a)
                        } else if (z in b.$inline) a.filterChildren(c),
                        q(a) && delete a.name;
                        else if ( - 1 != z.indexOf(":") && -1 == z.indexOf("cke")) {
                            a.filterChildren(c);
                            if ("v:imagedata" == z) {
                                if (z = a.attributes["o:href"]) a.attributes.src = z;
                                a.name = "img";
                                return
                            }
                            delete a.name
                        }
                        z in x && (a.filterChildren(c), m(a))
                    },
                    style: function(a) {
                        if (CKEDITOR.env.gecko) {
                            a = (a = a.onlyChild().value.match(/\/\* Style Definitions \*\/([\s\S]*?)\/\*/)) && a[1];
                            var c = {};
                            a && (a.replace(/[\n\r]/g, "").replace(/(.+?)\{(.+?)\}/g,
                            function(a, b, I) {
                                b = b.split(",");
                                a = b.length;
                                for (var d = 0; d < a; d++) CKEDITOR.tools.trim(b[d]).replace(/^(\w+)(\.[\w-]+)?$/g,
                                function(a, b, d) {
                                    b = b || "*";
                                    d = d.substring(1, d.length);
                                    d.match(/MsoNormal/) || (c[b] || (c[b] = {}), d ? c[b][d] = I: c[b] = I)
                                })
                            }), e.applyStyleFilter = function(a) {
                                var b = c["*"] ? "*": a.name,
                                d = a.attributes && a.attributes["class"];
                                b in c && (b = c[b], "object" == typeof b && (b = b[d]), b && a.addStyle(b, !0))
                            })
                        }
                        return ! 1
                    },
                    p: function(a) {
                        if (/MsoListParagraph/i.exec(a.attributes["class"]) || a.getStyle("mso-list") && !a.getStyle("mso-list").match(/^(none|skip)$/i)) {
                            var b = a.firstChild(function(a) {
                                return a.type == CKEDITOR.NODE_TEXT && !q(a.parent)
                            }); (b = b && b.parent) && b.addStyle("mso-list", "Ignore")
                        }
                        a.filterChildren(c);
                        u(a) || (d.enterMode == CKEDITOR.ENTER_BR ? (delete a.name, a.add(new CKEDITOR.htmlParser.element("br"))) : g(d["format_" + (d.enterMode == CKEDITOR.ENTER_P ? "p": "div")])(a))
                    },
                    div: function(a) {
                        var c = a.onlyChild();
                        if (c && "table" == c.name) {
                            var b = a.attributes;
                            c.attributes = CKEDITOR.tools.extend(c.attributes, b);
                            b.style && c.addStyle(b.style);
                            c = new CKEDITOR.htmlParser.element("div");
                            c.addStyle("clear", "both");
                            a.add(c);
                            delete a.name
                        }
                    },
                    td: function(a) {
                        a.getAncestor("thead") && (a.name = "th")
                    },
                    ol: p,
                    ul: p,
                    dl: p,
                    font: function(a) {
                        if (t(a.parent)) delete a.name;
                        else {
                            a.filterChildren(c);
                            var b = a.attributes,
                            d = b.style,
                            e = a.parent;
                            "font" == e.name ? (CKEDITOR.tools.extend(e.attributes, a.attributes), d && e.addStyle(d), delete a.name) : (d = (d || "").split(";"), b.color && ("#000000" != b.color && d.push("color:" + b.color), delete b.color), b.face && (d.push("font-family:" + b.face), delete b.face), b.size && (d.push("font-size:" + (3 < b.size ? "large": 3 > b.size ? "small": "medium")), delete b.size), a.name = "span", a.addStyle(d.join(";")))
                        }
                    },
                    span: function(a) {
                        if (t(a.parent)) return ! 1;
                        a.filterChildren(c);
                        if (q(a)) return delete a.name,
                        null;
                        if (t(a)) {
                            var b = a.firstChild(function(a) {
                                return a.value || "img" == a.name
                            }),
                            e = (b = b && (b.value || "l.")) && b.match(/^(?:[(]?)([^\s]+?)([.)]?)$/);
                            if (e) return b = h(e, b),
                            (a = a.getAncestor("span")) && / mso-hide:\s*all|display:\s*none /.test(a.attributes.style) && (b.attributes["cke:ignored"] = 1),
                            b
                        }
                        if (e = (b = a.attributes) && b.style) b.style = l([["line-height"], [/^font-family$/, null, r ? null: n(d.font_style, "family")], [/^font-size$/, null, r ? null: n(d.fontSize_style, "size")], [/^color$/, null, r ? null: n(d.colorButton_foreStyle, "color")], [/^background-color$/, null, r ? null: n(d.colorButton_backStyle, "color")]])(e, a) || "";
                        b.style || delete b.style;
                        CKEDITOR.tools.isEmpty(b) && delete a.name;
                        return null
                    },
                    b: g(d.coreStyles_bold),
                    i: g(d.coreStyles_italic),
                    u: g(d.coreStyles_underline),
                    s: g(d.coreStyles_strike),
                    sup: g(d.coreStyles_superscript),
                    sub: g(d.coreStyles_subscript),
                    a: function(a) {
                        var b = a.attributes;
                        b.name && b.name.match(/ole_link\d+/i) ? delete a.name: b.href && b.href.match(/^file:\/\/\/[\S]+#/i) && (b.href = b.href.replace(/^file:\/\/\/[^#]+/i, ""))
                    },
                    "cke:listbullet": function(a) {
                        a.getAncestor(/h\d/) && !d.pasteFromWordNumberedHeadingToList && delete a.name
                    }
                },
                attributeNames: [[/^onmouse(:?out|over)/, ""], [/^onload$/, ""], [/(?:v|o):\w+/, ""], [/^lang/, ""]],
                attributes: {
                    style: l(w ? [[/^list-style-type$/, null], [/^margin$|^margin-(?!bottom|top)/, null,
                    function(a, b, c) {
                        if (b.name in {
                            p: 1,
                            div: 1
                        }) {
                            b = "ltr" == d.contentsLangDirection ? "margin-left": "margin-right";
                            if ("margin" == c) a = v(c, a, [b])[b];
                            else if (c != b) return null;
                            if (a && !G.test(a)) return [b, a]
                        }
                        return null
                    }], [/^clear$/], [/^border.*|margin.*|vertical-align|float$/, null,
                    function(a, b) {
                        if ("img" == b.name) return a
                    }], [/^width|height$/, null,
                    function(a, b) {
                        if (b.name in {
                            table: 1,
                            td: 1,
                            th: 1,
                            img: 1
                        }) return a
                    }]] : [[/^mso-/], [/-color$/, null,
                    function(a) {
                        if ("transparent" == a) return ! 1;
                        if (CKEDITOR.env.gecko) return a.replace(/-moz-use-text-color/g, "transparent")
                    }], [/^margin$/, G], ["text-indent", "0cm"], ["page-break-before"], ["tab-stops"], ["display", "none"], r ? [/font-?/] : null], w),
                    width: function(a, c) {
                        if (c.name in b.$tableContent) return ! 1
                    },
                    border: function(a, c) {
                        if (c.name in b.$tableContent) return ! 1
                    },
                    "class": function(a, b) {
                        return b.classWhiteList && -1 != b.classWhiteList.indexOf(" " + a + " ") ? a: !1
                    },
                    bgcolor: k,
                    valign: w ? k: function(a, b) {
                        b.addStyle("vertical-align", a);
                        return ! 1
                    }
                },
                comment: CKEDITOR.env.ie ? k: function(a, b) {
                    var c = a.match(/<img.*?>/),
                    d = a.match(/^\[if !supportLists\]([\s\S]*?)\[endif\]$/);
                    return d ? (d = (c = d[1] || c && "l.") && c.match(/>(?:[(]?)([^\s]+?)([.)]?)</), h(d, c)) : CKEDITOR.env.gecko && c ? (c = CKEDITOR.htmlParser.fragment.fromHtml(c[0]).children[0], (d = (d = (d = b.previous) && d.value.match(/<v:imagedata[^>]*o:href=['"](.*?)['"]/)) && d[1]) && (c.attributes.src = d), c) : !1
                }
            }
        }
    },
    J = function() {
        this.dataFilter = new CKEDITOR.htmlParser.filter
    };
    J.prototype = {
        toHtml: function(a) {
            a = CKEDITOR.htmlParser.fragment.fromHtml(a);
            var c = new CKEDITOR.htmlParser.basicWriter;
            a.writeHtml(c, this.dataFilter);
            return c.getHtml(!0)
        }
    };
    CKEDITOR.cleanWord = function(a, c) {
        a = a.replace(/<!\[([^\]]*?)\]>/g, "\x3c!--[$1]--\x3e");
        CKEDITOR.env.gecko && (a = a.replace(/(\x3c!--\[if[^<]*?\])--\x3e([\S\s]*?)\x3c!--(\[endif\]--\x3e)/gi, "$1$2$3"));
        CKEDITOR.env.webkit && (a = a.replace(/(class="MsoListParagraph[^>]+>\x3c!--\[if !supportLists\]--\x3e)([^<]+<span[^<]+<\/span>)(\x3c!--\[endif\]--\x3e)/gi, "$1\x3cspan\x3e$2\x3c/span\x3e$3"));
        var b = new J,
        f = b.dataFilter;
        f.addRules(CKEDITOR.plugins.pastefromword.getRules(c, f));
        c.fire("beforeCleanWord", {
            filter: f
        });
        try {
            a = b.toHtml(a)
        } catch(d) {
            c.showNotification(c.lang.pastefromword.error)
        }
        a = a.replace(/cke:.*?".*?"/g, "");
        a = a.replace(/style=""/g, "");
        return a = a.replace(/<span>/g, "")
    }
})();