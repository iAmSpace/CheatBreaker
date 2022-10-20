import subprocess
import sys
import os

ver = None

def get_git_revision_hash() -> str:
    return subprocess.check_output(['git', 'rev-parse', 'HEAD']).decode('ascii').strip()

def get_git_revision_short_hash() -> str:
    return subprocess.check_output(['git', 'rev-parse', '--short', 'HEAD']).decode('ascii').strip()

versionPath = os.path.dirname(sys.argv[0]) + "/version.txt"
if os.path.exists(versionPath):
    with open(versionPath, "r", encoding="utf8") as fp:
        ver = fp.read()
else:
#    ver = subprocess.run(["git", "describe", "--tags", "--dirty"], capture_output=True, text=True).stdout or "UNKNOWN-" + subprocess.run(["git", "describe", "--always", "--dirty"], capture_output=True, text=True).stdout or "UNKNOWN"
    ver = subprocess.run(["git", "rev-parse", "--short", "HEAD"], capture_output=True, text=True).stdout or "UNKNOWN-" + subprocess.run(["git", "describe", "--always", "--dirty"], capture_output=True, text=True).stdout or "UNKNOWN"

ver = ver.strip()
if ver[0] == "v":
    ver = ver[1:]

props = open("src/main/resources/assets/minecraft/client/properties/app.properties",'w') # This file will be accessed in the client - it is useful to set it's values now
props.write("git.commit.id.abbrev=" + get_git_revision_short_hash() + "\n")
props.write("git.commit.id=" + get_git_revision_hash() + "\n")
props.write("git.branch=master")
props.close()

print(ver)