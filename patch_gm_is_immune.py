import os
import re

directory = r'D:\.gemini\antigravity\scratch\GMMythicevent\Mythicmobs\ClassSkill\Skills'

# This script will append @Targeted ?!gm_is_immune to any potion or orbital line 
# that does NOT already have a targeter (like @self, @Targeted, etc.)

for root, dirs, files in os.walk(directory):
    for f in files:
        if not f.endswith('.yml'): continue
        path = os.path.join(root, f)
        
        with open(path, 'r', encoding='utf-8') as file:
            content = file.read()
            
        lines = content.split('\n')
        new_lines = []
        modified = False
        
        for line in lines:
            # check if it's a potion or orbital line
            if re.search(r'^\s*-\s*(potion|orbital)\s*\{', line):
                # if it already has @, don't touch it
                if '@' not in line:
                    line = line + " @Targeted ?!gm_is_immune"
                    modified = True
            
            new_lines.append(line)
            
        if modified:
            with open(path, 'w', encoding='utf-8') as file:
                file.write('\n'.join(new_lines))
            print(f"Patched {f}")

print("Done.")
