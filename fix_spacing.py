import re

path = r"c:\Users\ADMINI~1.DES\AppData\Local\Temp\scp54470\plugins\DeluxeMenus\gui_menus\shop_kynangvinhvien.yml"

with open(path, 'r', encoding='utf-8') as f:
    content = f.read()

# Fix the missing newlines before top-level items under "items:"
# Currently they look like:
# items:  glass_border:
#       - 53  menu:
#       - "[close]"  bang1_owned:

# We want to replace occurrences where "  " followed by an identifier and colon appears on the same line as something else.
# A safe way is to find a non-newline character, followed by "  ", followed by word characters and a colon.
# Then insert a newline before the "  ".
# Let's use re.sub

fixed_content = re.sub(r'([^\n])(  [a-zA-Z0-9_]+:)', r'\1\n\2', content)

with open(path, 'w', encoding='utf-8') as f:
    f.write(fixed_content)

print("Fixed spacing issue.")
