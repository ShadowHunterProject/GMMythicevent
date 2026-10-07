import os
import re

directory = r'D:\.gemini\antigravity\scratch\GMMythicevent\Mythicmobs\ClassSkill\Skills'

for root, dirs, files in os.walk(directory):
    for f in files:
        if not f.endswith('.yml'): continue
        path = os.path.join(root, f)
        
        with open(path, 'r', encoding='utf-8') as file:
            content = file.read()
            
        lines = content.split('\n')
        new_lines = []
        i = 0
        modified = False
        while i < len(lines):
            line = lines[i]
            new_lines.append(line)
            if re.match(r'^\s*TargetConditions:\s*$', line):
                # find the indentation of the next line
                next_line_idx = i + 1
                while next_line_idx < len(lines) and lines[next_line_idx].strip() == '':
                    next_line_idx += 1
                
                if next_line_idx < len(lines):
                    next_line = lines[next_line_idx]
                    match = re.match(r'^(\s*)-', next_line)
                    if match:
                        indent = match.group(1)
                        # Check if solo_is_my_summon is already in this block
                        has_summon = False
                        check_idx = next_line_idx
                        while check_idx < len(lines) and (lines[check_idx].strip() == '' or lines[check_idx].startswith(indent)):
                            if 'solo_is_my_summon' in lines[check_idx]:
                                has_summon = True
                                break
                            check_idx += 1
                            
                        if not has_summon:
                            new_lines.append(f"{indent}- solo_is_my_summon false")
                            modified = True
            i += 1
            
        if modified:
            with open(path, 'w', encoding='utf-8') as file:
                file.write('\n'.join(new_lines))
            print(f"Patched {f}")

print("Done.")
