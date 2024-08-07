package com.samy.klivvrandroidchallenge.data.model.converter

import com.samy.klivvrandroidchallenge.data.model.City

// Trie class is initialized as a TrieNode.
class Trie {
    private val root = TrieNode()

    fun insert(prefix: String, Country: City) {
        var node = root
        for (char in prefix) {
            node = node.children.getOrPut(char) { TrieNode() }
            node.cities.add(Country)
        }
    }

    fun search(prefix: String): List<City> {
        var node = root
        for (char in prefix) {
            node = node.children[char] ?: return emptyList()
        }
        return collectCities(node)
    }

    private fun collectCities(node: TrieNode): List<City> {
        val result = mutableListOf<City>()
        result.addAll(node.cities)
        for (child in node.children.values) {
            result.addAll(collectCities(child))
        }
        return result
    }


}