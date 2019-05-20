#! /usr/bin/env python
# -*- coding: utf-8 -*-

import urllib.request
import sys

'''默认代理IP资源文件'''
DEFAULT_PROXY_RESOURCE_URL = 'https://raw.githubusercontent.com/ym/chnroutes2/master/chnroutes.txt'

'''默认目标URL'''
DEFAULT_TARGET_URL = 'https://m.gong7688.com/pages/?inviter=961990#!/book/cover/1260'

'''默认获取代理参数'''
PROXY_ARGS = {'list': False, 'url': False}

'''默认点击操作'''
CHECK_ARGS = {'proxy_url': DEFAULT_PROXY_RESOURCE_URL, 'url': DEFAULT_TARGET_URL, 'time': -1, 'repeat': 1, 'callback': ''}

def start_with_num(string):
    '''判断字符串是否已数字开头'''
    return string.startswith('0') or string.startswith('2') or string.startswith('3') \
        or string.startswith('4') or string.startswith('5') or string.startswith('6') \
        or string.startswith('7') or string.startswith('8') or string.startswith('9')

def get_proxy_ip_list(url):
    return list(filter(start_with_num, list(map(lambda b: str(b, encoding = 'utf-8'), urllib.request.urlopen(url).readlines()))))

def build_request(proxy_addr):
    '''设置代理IP'''
    proxy = urllib.request.ProxyHandler({'http': proxy_addr})
    opener = urllib.request.build_opener(proxy)
    urllib.request.install_opener(opener)
    return urllib.request

def start_with_any(keys, string):
    '''字符串以keys中任意一个值为开头'''
    for k in keys:
        if string.startswith(k):
            return True
    return False

def get_args_list(list1, index1, index2):
    '''根据两个索引值获取子列表'''
    sub_list = []
    if index1 < 0:
        return sub_list
    if index1 > index2:
        sub_list = list1[index1 + 1:]
    else:
        sub_list = list1[index1 + 1:index2]
    return sub_list

def get_args():
    help_msg = '''获取命令行参数
    命令行参数格式：-type [arg]

    * 获取代理信息：
        -proxy [arg]
        arg 为 list 时，获取代理IP列表
        arg 为 url 时，获取代理IP资源文件URL
        默认为url。

    * 执行点击操作：
        -check [arg]
        arg 为key=value格式，key、value均不允许为空：
            proxy_url：value为代理资源URL，默认为DEFAULT_PROXY_RESOURCE_URL
            url：value为目标URL，默认为DEFAULT_TARGET_URL
            time：value为点击次数，小于0则为使所有代理请求一次目标URL，默认为-1
            repeat：value为重复点击次数，只允许为正整数，默认为1
            callback：value为回调URL，使用GET请求，参数为：status-点击请求目标URL的HTTP状态码，如：https://myapp.com/demo?status=200，默认为空'''

    error_msg = None
    '''排除掉脚本名称'''
    input_args_list = sys.argv[1:]
    '''是否为help命令'''
    try:
        input_args_list.index('-help')
        input_args_list.index('-h')
    except BaseException:
        pass
    else:
        return PROXY_ARGS, CHECK_ARGS, help_msg

    try:
        proxy_index = input_args_list.index('-proxy')
    except BaseException:
        '''没有-proxy参数，则不改变PROXY_ARGS'''
        proxy_index = -1

    try:
        check_index = input_args_list.index('-check')
    except BaseException:
        '''没有-check参数，则不改变CHECK_ARGS'''
        check_index = -1
    
    if proxy_index == -1 and check_index == -1:
        '''没有check、proxy参数，则直接返回默认值'''
        return PROXY_ARGS, CHECK_ARGS, error_msg

    '''check参数'''
    input_check_args = get_args_list(input_args_list, check_index, proxy_index)
    if input_check_args:
        for item in input_check_args:
            keyValue = item.split('=')
            if len(keyValue) != 2:
                error_msg = 'The key[\'' + item + '\'] of the input parameter is illegal.'
                return PROXY_ARGS, CHECK_ARGS, error_msg
            if keyValue[0] in CHECK_ARGS:
                '''参数正确，则设置输入值'''
                keyValue = item.split('=')
                if not keyValue[1]:
                    error_msg = 'The value[\'' + item + '\'] of the input parameter is not allowed to be empty.'
                    return PROXY_ARGS, CHECK_ARGS, error_msg
                CHECK_ARGS[keyValue[0]] = keyValue[1]
    
    '''proxy参数'''
    input_proxy_args = get_args_list(input_args_list, proxy_index, check_index)
    if input_proxy_args:
        for item in input_proxy_args:
            if item in PROXY_ARGS:
                '''参数正确，则设置输入值'''
                PROXY_ARGS[item] = True

    return PROXY_ARGS, CHECK_ARGS, error_msg

def callback(url, status):
    '''回调'''
    if url:
        print('回调接口：', url)
        urllib.request.urlopen(url + '?status=' + status)

if __name__ == "__main__":
    '''获取命令行参数'''
    input_proxy_args, input_check_args, error_msg = get_args()
    repeat = input_check_args['repeat']
    if repeat < 1:
        print('重复次数必须大于0\nfor help use -help/-h')
        sys.exit(0)
    time = input_check_args['time']

    if error_msg:
        '''参数输入错误'''
        print(error_msg)
        print('\nfor help use -help/-h')
        sys.exit(0)
    
    proxy_ip_list = get_proxy_ip_list(input_check_args['proxy_url'])

    '''展示代理信息'''
    if input_proxy_args['url']:
        print('代理资源路径：', input_check_args['proxy_url'])

    if input_proxy_args['list']:
        print('代理资源列表：')
        for i in proxy_ip_list:
            print(i)

    url = input_check_args['url']
    for proxy_addr in proxy_ip_list:
        http_status = build_request(proxy_addr).urlopen(input_check_args['url']).getcode()
        callback(url, http_status)

