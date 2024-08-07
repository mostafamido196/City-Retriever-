package com.samy.klivvrandroidchallenge.data.model.converter

import com.samy.klivvrandroidchallenge.data.model.City

// Example of Trie node for efficient prefix searching
class TrieNode {
    val children: MutableMap<Char, TrieNode> = mutableMapOf()
    val cities: MutableList<City> = mutableListOf()
}