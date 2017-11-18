#!/bin/bash

lein do clean, cljsbuild once min
cp -r resources/public/* docs/
