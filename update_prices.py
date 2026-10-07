import re

path = r"c:\Users\ADMINI~1.DES\AppData\Local\Temp\scp54470\plugins\DeluxeMenus\gui_menus\shop_kynangvinhvien.yml"

with open(path, 'r', encoding='utf-8') as f:
    content = f.read()

# We can split the file by item keys.
# Each item starts with "  [key]:"
items = re.split(r'\n(?=  [a-zA-Z0-9_]+:)', '\n' + content)
if items[0].strip() == '':
    items = items[1:]

new_content = ""

for item in items:
    if "left_click_requirement:" in item and "GIÁ BÁN TRỰC TIẾP:" in item:
        # This is an unowned skill with our injected 999 price
        # Let's find its rarity
        rarity_match = re.search(r'Độ hiếm:.*?(Thường|Huyền Thoại|Thần Thoại|Thượng Phẩm)', item, re.IGNORECASE)
        price = 999
        if rarity_match:
            rarity = rarity_match.group(1).lower()
            if rarity == "thường":
                price = 300
            elif rarity == "huyền thoại":
                price = 800
            elif rarity == "thần thoại":
                price = 1500
            elif rarity == "thượng phẩm":
                price = 2200
        
        # Replace 999 with the new price
        item = item.replace("999 Xu", f"{price} Xu")
        item = item.replace('output: "999"', f'output: "{price}"')
        item = item.replace("999", str(price)) # Catch any remaining 999s like in the deny command or points take command

    new_content += item

# Remove the leading newline we added for splitting
if new_content.startswith('\n'):
    new_content = new_content[1:]

with open(path, 'w', encoding='utf-8') as f:
    f.write(new_content)

print("Updated prices successfully.")
