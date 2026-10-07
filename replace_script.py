import os
import re

directory = r'D:\.gemini\antigravity\scratch\GMMythicevent\Mythicmobs\ClassSkill'

for root, _, files in os.walk(directory):
    for f in files:
        if f.endswith('.yml'):
            path = os.path.join(root, f)
            with open(path, 'r', encoding='utf-8') as file:
                content = file.read()
            
            new_content = re.sub(r'(\s*-\s*)pull(\{)', r'\1solo_pull\2', content)
            new_content = re.sub(r'(\s*-\s*)push(\{)', r'\1solo_push\2', new_content)
            
            if new_content != content:
                with open(path, 'w', encoding='utf-8') as file:
                    file.write(new_content)
                print(f'Updated {f}')
