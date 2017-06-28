#!/usr/bin/env python


# --- bug bountry index ---------------
# https://www.bugcrowd.com/bug-bounty-list/
# https://www.vulnerability-lab.com/list-of-bug-bounty-programs.php
# https://internetbugbounty.org/
# https://bugcrowd.com/programs
# https://hackerone.com/bug-bounty-programs
# https://hackerone.com/directory?query=type%3Ahackerone&sort=published_at%3Adescending&page=1
# --- password database ---------------
# pwdb() {
#     grep -a --include=*.{slq,csv,txt} $1 -R /d/Leaks
# }
# --- learning from others ------------
# https://bugcrowd.com/leaderboard

# default setup:
#     directory structure under $M_HOME/ (default: ~/.m):
#         conf.yml
#         targets.yml
#         tech.yml
#         ${target}/tape-*.zip
#     config-default(path):
#            retrieve from ${M_HOME}/targets.yml:target:path
#         OR retrieve from ${M_HOME}/conf.yml:path
#         OR use command's default value
#     all commands:
#         default conf=${M_HOME}/conf.yml
#         commands with '--in' have '--stdin' option
#         commands with '--out' have '--stdout' option
#         commands with '--in' allow for variable expressions
#         commands with '--out' allow for variable expressions
#     all yaml loads:
#         allows for file inclusion via _include: "path.yml"
#         paths allow for variable expressions
#         for example: matcher.yml could include all kinds of matching logic
#
# m default-config
#     prints all core functionality configuration
#     prints all per-module configuration
#
#         targets: ${M_HOME}/targets.yml
#         tapes:   ${M_HOME}/${target}/tape-*.zip
#
# m index [out:${M_HOME}/targets.yml]
#     indexes all programs to a file
#
# m tape-start [target:none]
#              [port:8888]
#              [out-pattern:${M_HOME}/${target}/tape-${now}.zip]
#     begins proxy daemon on ${port} which writes proxied traffic to an archive file
#     prints "tape-port<tab>out"
#
# m tape-stop [target:none]
#             [port:8888]
#     stops all proxy daemons active for ${target} or on ${port}
#
# m [module]
#   [targets:${M_HOME}/targets.yml]
#   [in:${M_HOME}/${target}/${module.in}.yml]
#   [out:${M_HOME}/${target}/${module.out}.yml]
#
# modules:
#   tech (in=${M_HOME}/tech-matcher.yml, out=${M_HOME}/${target}/tech.yml)
#     for all ${targets}, opens each file of pattern ${tapes} for reading
#     applies matching logic using the given ${in} tech-matcher.yml-file
#     matcher evaluated data templates, as per logic, are written to ${out} tech.yml-files
#   vulns (in=${M_HOME}/${target}/tech.yml, out=${M_HOME}/${target}/vulns.yml)
#     for all ${targets}, opens each ${in} tech.yml-file for reading
#     searches for vulnerabilities for techs, writes vulnerabilities to ${out} vulns.yml-files
#
# m index
# m tape-start example.com
# # do browsing with proxy enabled
# m tape-stop example.com
# m tech
# m vulns --stdout
