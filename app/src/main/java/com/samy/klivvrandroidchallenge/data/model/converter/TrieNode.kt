package com.samy.klivvrandroidchallenge.data.model.converter

import com.samy.klivvrandroidchallenge.data.model.City

// Example of Trie node for efficient prefix searching
class TrieNode {
    val children = mutableMapOf<Char, TrieNode>()
    val cities = mutableSetOf<City>()  // Changed to a set to avoid duplicates

}