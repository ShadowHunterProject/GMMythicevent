import re

path = r"c:\Users\ADMINI~1.DES\AppData\Local\Temp\scp54470\plugins\DeluxeMenus\gui_menus\shop_kynangvinhvien.yml"

with open(path, 'r', encoding='utf-8') as f:
    lines = f.readlines()

new_lines = []
current_item = None
is_unowned_skill = False
skip_mode = False

i = 0
while i < len(lines):
    line = lines[i]
    
    # Check for item definition
    match = re.match(r'^  ([a-zA-Z0-9_]+):', line)
    if match:
        current_item = match.group(1)
        if current_item not in ["glass_border", "menu"] and not current_item.endswith("_owned"):
            is_unowned_skill = True
        else:
            is_unowned_skill = False

    if is_unowned_skill:
        if line.strip() == "- \"  &ftại thương nhân bán theo thời gian &e/goiqua\"":
            new_lines.append(line)
            new_lines.append('      - ""\n')
            new_lines.append('      - "&a&l💰 GIÁ BÁN TRỰC TIẾP: &e999 Xu"\n')
            new_lines.append('      - "&b➤ Click để MUA VĨNH VIỄN kỹ năng này!"\n')
            i += 1
            continue
            
        if line.strip().startswith("left_click_commands:"):
            # We skip the next lines until we hit another property or item
            req = f"""    left_click_requirement:
      requirements:
        xu:
          type: ">="
          input: "%playerpoints_points%"
          output: "999"
          deny_commands:
            - "[message] &cBạn không đủ 999 Xu để mua kỹ năng này!"
            - "[sound] ENTITY_VILLAGER_NO"
    left_click_commands:
      - "[console] points take %player_name% 999"
      - "[console] lp user %player_name% permission set kynang.{current_item}"
      - "[message] &aBạn đã mua vĩnh viễn kỹ năng thành công!"
      - "[sound] ENTITY_PLAYER_LEVELUP"
      - "[refresh]"
"""
            new_lines.append(req)
            
            # skip the old left_click_commands
            i += 1
            while i < len(lines) and lines[i].startswith("      -"):
                i += 1
            continue

    new_lines.append(line)
    i += 1

with open(path, 'w', encoding='utf-8') as f:
    f.writelines(new_lines)

print("Done")
