path = r"c:\Users\ADMINI~1.DES\AppData\Local\Temp\scp54470\plugins\DeluxeMenus\gui_menus\shop_kynangvinhvien.yml"

with open(path, 'r', encoding='utf-8') as f:
    lines = f.readlines()

new_lines = []
context = []

for line in lines:
    clean = line.strip()
    if not clean:
        continue
    
    # Determine the indentation level
    indent = 0
    
    if clean.startswith("menu_title:") or clean.startswith("open_command:") or clean.startswith("size:") or clean.startswith("items:"):
        indent = 0
        context = [clean.split(":")[0]]
    elif clean == "- chonkynang":
        indent = 2
    elif context and context[0] == "items":
        # We are inside items
        if clean.endswith(":") and not clean.startswith("-") and not clean.split(":")[0] in ["material", "display_name", "slot", "slots", "item_flags", "lore", "priority", "view_requirement", "left_click_requirement", "left_click_commands", "requirements", "owned", "xu", "type", "permission", "input", "output", "deny_commands"]:
            # This is an item ID
            indent = 2
            context = ["items", "item_id"]
        elif clean.split(":")[0] in ["material", "display_name", "slot", "slots", "item_flags", "lore", "priority", "view_requirement", "left_click_requirement", "left_click_commands"]:
            indent = 4
            if clean.startswith("slots:") or clean.startswith("item_flags:") or clean.startswith("lore:") or clean.startswith("left_click_commands:"):
                context = ["items", "item_id", "list"]
            elif clean.startswith("view_requirement:") or clean.startswith("left_click_requirement:"):
                context = ["items", "item_id", "requirement"]
            else:
                context = ["items", "item_id"]
        elif clean == "requirements:":
            indent = 6
            context = ["items", "item_id", "requirement", "requirements"]
        elif clean.startswith("owned:") or clean.startswith("xu:"):
            indent = 8
            context = ["items", "item_id", "requirement", "requirements", "prop"]
        elif clean.startswith("type:") or clean.startswith("permission:") or clean.startswith("input:") or clean.startswith("output:") or clean.startswith("deny_commands:"):
            indent = 10
            if clean.startswith("deny_commands:"):
                context = ["items", "item_id", "requirement", "requirements", "prop", "deny_commands"]
        elif clean.startswith("-"):
            if context[-1] == "list":
                indent = 6
            elif context[-1] == "deny_commands":
                indent = 12
            else:
                indent = 6 # fallback for lists inside item
        else:
            indent = 4 # fallback
            
    new_lines.append((" " * indent) + clean + "\n")

with open(path, 'w', encoding='utf-8') as f:
    f.writelines(new_lines)

print("Formatting fixed!")
