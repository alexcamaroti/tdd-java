package br.com.camaroti.alex.tdd.auctionhouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public @Data class User {

	private int id;
	@NonNull private String name;
}
