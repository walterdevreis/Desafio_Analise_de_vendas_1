package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) throws IOException {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Sale> list = new ArrayList<>();
			
			System.out.println("\nCinco primeiras vendas de 2016 de maior preço médio");
			
			String line = br.readLine();
			while(line != null) {
				String[] file = line.split(",");
				Integer month = Integer.parseInt(file[0]);
				Integer year = Integer.parseInt(file[1]);
				String seller = file[2];
				Integer items = Integer.parseInt(file[3]);
				Double total = Double.parseDouble(file[4]);
				
				list.add(new Sale(month, year, seller, items, total));
				
				line = br.readLine();
			}
			
			Comparator<Double> comp = (s1, s2) -> s1.compareTo(s2);
			
			List<Sale> sale = list.stream()
					.filter(s -> s.getYear() == 2016)
					.sorted((s1, s2) -> s2.averagePrice().compareTo(s1.averagePrice()))
					.limit(5)
					.collect(Collectors.toList());
				
				sale.forEach(System.out::print);
				
			System.out.println();
				
			double sum = list.stream()
					.filter(s -> (s.getSeller().equals("Logan") && s.getMonth() == 1) || (s.getSeller().equals("Logan") && s.getMonth() == 7))
					.mapToDouble(s -> s.getTotal())
					.reduce(0.0, (x, y) -> x + y);
			
			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", sum));
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		} 
		
		sc.close();
	}
}
