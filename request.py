#! /usr/bin/env python
# -*- coding: utf-8 -*-

import urllib.request

'''默认代理IP资源文件'''
DEFAULT_PROXY_RESOURCE_URL = 'https://raw.githubusercontent.com/ym/chnroutes2/master/chnroutes.txt'

'''默认目标URL'''
DEFAULT_TARGET_URL = 'https://m.gong7688.com/pages/?inviter=961990#!/book/cover/1260'

def start_with(string):
    '''判断字符串是否已数字开头'''
    return string.startswith('0') or string.startswith('2') or string.startswith('3') \
        or string.startswith('4') or string.startswith('5') or string.startswith('6') \
        or string.startswith('7') or string.startswith('8') or string.startswith('9')

def b_s(b):
    return str(b, encoding = 'utf-8')

def get_proxy_ip_list(url):
    print('开始获取代理IP列表。。。')
    '''获取代理IP列表'''
    ip_byte_list = urllib.request.urlopen(url).readlines()
    print('列表长度：%d', len(ip_byte_list))
    return filter(start_with, list(map(b_s, ip_byte_list)))

def build_request(proxy_addr):
    '''设置代理IP'''
    proxy = urllib.request.ProxyHandler({'http': proxy_addr})
    opener = urllib.request.build_opener(proxy)
    urllib.request.install_opener(opener)
    return urllib.request


if __name__ == "__main__":
    '''每个代理IP请求一次'''
    for proxy_addr in get_proxy_ip_list(DEFAULT_PROXY_RESOURCE_URL):
        print('开始访问%s', proxy_addr)
        http_status = build_request(proxy_addr).urlopen(DEFAULT_TARGET_URL).getcode()
        if http_status == 200:
            print('访问完成！！！\n')
        else:
            print('响应异常！')

