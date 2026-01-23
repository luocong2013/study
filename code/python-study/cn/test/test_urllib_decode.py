from urllib import parse


def url_decode():
    encoded_url = "%5B%7B%22app_food_code%22%3A%222025082700101001%22%2C%22app_spu_code%22%3A%222025082700101001%22%2C%22sku_id%22%3A%222025082700101001%22%7D%5D"
    # URL 解码
    decoded_url = parse.unquote(encoded_url)
    print("解码后 URL:", decoded_url)


def url_ecode():
    # 示例 URL
    original_url = """
    [{"app_food_code":"2025082700101001","app_spu_code":"2025082700101001","sku_id":"2025082700101001"},{"app_food_code":"9787572806391001","app_spu_code":"9787572806391001","sku_id":"9787572806391001"}]
    """

    # URL 编码
    encoded_url = parse.quote(original_url)
    print("原始 URL:", original_url)
    print("编码后 URL:", encoded_url)


if __name__ == "__main__":
    url_ecode()
