import os
import re

directory = r'D:\.gemini\antigravity\scratch\GMMythicevent\Mythicmobs\ClassSkill\Skills'

for root, dirs, files in os.walk(directory):
    for f in files:
        if not f.endswith('.yml'): continue
        path = os.path.join(root, f)
        
        with open(path, 'r', encoding='utf-8') as file:
            content = file.read()
            
        new_content = content
        
        # Replace throw with solo_throw
        new_content = re.sub(r'-\s*throw\{', '- solo_throw{', new_content)
        
        # Replace velocity with solo_velocity
        new_content = re.sub(r'-\s*velocity\{', '- solo_velocity{', new_content)
        
        if content != new_content:
            with open(path, 'w', encoding='utf-8') as file:
                file.write(new_content)
            print(f"Patched {f}")

print("Done patching mechanics.")
