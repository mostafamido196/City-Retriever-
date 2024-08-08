package com.samy.klivvrandroidchallenge.data.model.converter

import com.samy.klivvrandroidchallenge.data.model.City
import com.samy.klivvrandroidchallenge.util.Utils.myLog


// Trie class is initialized as a TrieNode.
class Trie {
    private val root = TrieNode()

    // Add a city to the Trie
    fun insert(city: City) {
        var node = root
        for (char in city.name.lowercase()) {
            node = node.children.getOrPut(char) { TrieNode() }
        }
        node.cities.add(city)
    }

    // Search for cities containing the term and return a List of City
    fun search(term: String): List<City> {
        var node = root
        for (char in term.lowercase()) {
            node = node.children[char] ?: return emptyList()
        }
        return collectCities(node).sortedWith(compareBy<City> { it.name }.thenBy { it.country })
    }

    // Collect all cities from a given TrieNode
    private fun collectCities(node: TrieNode): List<City> {
        val result = mutableListOf<City>()
        val stack = mutableListOf(node)
        while (stack.isNotEmpty()) {
            val currentNode = stack.removeAt(stack.size - 1)
            result.addAll(currentNode.cities)
            stack.addAll(currentNode.children.values)
        }
        return result
    }


}