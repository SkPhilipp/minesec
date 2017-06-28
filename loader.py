#!/usr/bin/env python
import yaml
import re
import os.path

MATCH_PREFIX = "\$\{"
MATCH_SUFFIX = "\}"

RESOLVE_SEPARATOR = "/"


def resolve(reference, variables):
    """
    >>> resolve("company/name", {"company": {"name": "MineSec"}})
    'MineSec'
    >>> resolve("invalid/key", {"company": {"name": "MineSec"}}) is None
    True
    """
    current = variables
    expressions = reference.split(RESOLVE_SEPARATOR)
    for expression in expressions:
        if expression in current:
            current = current[expression]
        else:
            return None
    return current


def interpolate(template, variables):
    """
    >>> interpolate("Mine${suffix}", {"suffix": "Sec"})
    'MineSec'
    """
    interpolated = template
    regex = re.compile(MATCH_PREFIX + "(.+?)" + MATCH_SUFFIX)
    for match in re.finditer(regex, interpolated):
        for group in match.groups():
            interpolated = re.sub(MATCH_PREFIX + group + MATCH_SUFFIX, resolve(group, variables), interpolated)
    return interpolated


def load(file_path):
    """
    >>> resource = load("./test-resources/loader.yml")
    >>> resolve("company/name", resource)
    'MineSec'
    >>> load("./test-resources/invalid/path.yml") is None
    True
    """
    if os.path.isfile(file_path):
        with open(file_path, 'r') as stream:
            return yaml.load(stream)
    else:
        return None


def config(file_paths, reference, default=None):
    """
    >>> config(["./test-resources/loader.yml", "./test-resources/loader2.yml"], "company/name")
    'MineSec'
    >>> config(["./test-resources/loader.yml", "./test-resources/loader2.yml"], "company/alternative")
    'MineSec3'
    >>> config(["./test-resources/loader.yml", "./test-resources/loader2.yml"], "invalid/key", "Nice1")
    'Nice1'
    """
    for file_path in file_paths:
        resource = load(file_path)
        if resource is not None:
            subresource = resolve(reference, resource)
            if subresource is not None:
                return subresource
    return default

if __name__ == "__main__":
    import doctest

    doctest.testmod()
