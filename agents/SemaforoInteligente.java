// ----------------------------------------------------------------------------
// Copyright (C) 2015 Louise A. Dennis and Michael Fisher 
// 
// This file is part of Gwendolen
//
// Gwendolen is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
// 
// Gwendolen is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with Gwendolen if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
// 
// To contact the authors:
// http://www.csc.liv.ac.uk/~lad
//----------------------------------------------------------------------------

package semaforo_inteligente;

import ail.mas.DefaultEnvironment;
import ail.syntax.Action;
import ail.syntax.Unifier;
import ail.syntax.Term;
import ail.syntax.VarTerm;
import ail.syntax.NumberTerm;
import ail.syntax.NumberTermImpl;
import ail.syntax.StringTermImpl;
import ail.syntax.Predicate;
import ail.util.AILexception;

import java.util.Random;

/**
 * This is a simple class representing a search and rescue environment on a
 * grid. For use
 * with the Gwendolen Tutorials.
 * 
 * @author lad
 *
 */
public class SemaforoInteligente extends DefaultEnvironment {
	String logname = "semaforo_inteligente.SemaforoInteligente";

	public final double duracaoTurno = 15;
	private double tempoFluxoSmf1 = duracaoTurno / 2;
	private double tempoFluxoSmf2 = duracaoTurno / 2;

	private Pedido pedido1;
	private Pedido pedido2;

	private int iteracaoPonderacao = 1;

	Cronometro cronometro = new Cronometro();

	/*
	 * (non-Javadoc)
	 * 
	 * @see ail.mas.DefaultEnvironment#executeAction(java.lang.String,
	 * ail.syntax.Action)
	 */
	public Unifier executeAction(String agName, Action act) throws AILexception {
		Unifier u = new Unifier();

		if (act.getFunctor().equals("ligar")) {
			cronometro.start(tempoFluxoSmf1, tempoFluxoSmf2);

			Predicate fimTurno = new Predicate("fimTurno");
			addPercept(agName, fimTurno);
		}

		if (act.getFunctor().equals("ponderar")) {
			iteracaoPonderacao = 1;

			Random rand = new Random();

			double descontoTempo = rand.nextDouble();
			double incrementoTempo = rand.nextDouble();

			double duracaoFluxoLivre = agName.equals("agSemaforo1") ? 
			tempoFluxoSmf1 : tempoFluxoSmf2;

			double tempoUtil = Math.ceil(
					duracaoFluxoLivre * (1.5 + incrementoTempo) * descontoTempo);

			if (duracaoFluxoLivre > tempoUtil) {
				System.out.println(agName + ": desejo menos tempo, " + tempoUtil + "s é o suficiente");
				
				if (agName.equals("agSemaforo1"))
					pedido1 = new Pedido(TipoPedido.CEDER, tempoUtil);
				else
					pedido2 = new Pedido(TipoPedido.CEDER, tempoUtil);
			} else {
				System.out.println(agName + ": desejo mais tempo, " + tempoUtil + "s é o suficiente");
				
				if (agName.equals("agSemaforo1"))
					pedido1 = new Pedido(TipoPedido.PEDIR, tempoUtil);
				else
					pedido2 = new Pedido(TipoPedido.PEDIR, tempoUtil);

			}
		}
		
		if (act.getFunctor().equals("mediar")) {
			if (pedido1 == null || pedido2 == null) {
				Predicate impasse = new Predicate("impasse");
				addPercept(agName, impasse);

			} else if (pedido1.tipo == pedido2.tipo) {
				boolean estaEstourandoTempo = 
						pedido1.tempoPedido + pedido2.tempoPedido > duracaoTurno;  
					
				if (!estaEstourandoTempo) {
					mudarTempos(pedido1.tempoPedido, pedido2.tempoPedido);
				} else {
					System.out.println("Tempo estourando: repensar!");
					
					// Obrigando, à força, os semáforos diminuírem o tempo pedido
					ponderarNovamente();
					
					Predicate impasse = new Predicate("impasse");
					addPercept(agName, impasse);
				}

				if (pedido1.tipo == TipoPedido.CEDER) {
					mudarTempos(pedido1.tempoPedido, pedido2.tempoPedido);
				} else if (pedido1.tipo == TipoPedido.PEDIR){
					estaEstourandoTempo = 
						pedido1.tempoPedido + pedido2.tempoPedido > duracaoTurno;  
					
					if (!estaEstourandoTempo) {
						mudarTempos(pedido1.tempoPedido, pedido2.tempoPedido);
					} else {
						System.out.println("Tempo estourando: repensar!");
						
						// Obrigando, à força, os semáforos diminuírem o tempo pedido
						ponderarNovamente();
						
						Predicate impasse = new Predicate("impasse");
						addPercept(agName, impasse);

						return u;
					}
				}
			} else {
				if (pedido1.tipo == TipoPedido.PEDIR) {
					double tempoLivre = duracaoTurno - pedido2.tempoPedido;
					if (pedido1.tempoPedido <= tempoLivre) 
						mudarTempos(pedido1.tempoPedido, pedido2.tempoPedido);
					else
						mudarTempos(tempoLivre, pedido2.tempoPedido);
				} else if (pedido1.tipo == TipoPedido.CEDER) {
					double tempoLivre = duracaoTurno - pedido1.tempoPedido;
					if (pedido2.tempoPedido <= tempoLivre) 
						mudarTempos(pedido1.tempoPedido, pedido2.tempoPedido);
					else
						mudarTempos(pedido1.tempoPedido, tempoLivre);
				}
			}
		}
		
		super.executeAction(agName, act);

		return u;
	}

	public void mudarTempos(double t1, double t2) {
		tempoFluxoSmf1 = t1;
		tempoFluxoSmf2 = t2;

		if (t1 + t2 <= duracaoTurno) {
			System.out.println("Resolução final: " + t1 + ", " + t2);
		}
	}

	public void ponderarNovamente() {
		iteracaoPonderacao++;

		double taxaDesconto = 0.025;

		double novaProposta = Math.ceil(
			pedido1.tempoPedido * (1.0 - taxaDesconto * iteracaoPonderacao)
		);
			
		System.out.println("agSemaforo1: Nova proposta - " + novaProposta);
		
		pedido1.tempoPedido = novaProposta;
		pedido1.tipo = TipoPedido.PEDIR;



		novaProposta = Math.ceil(
			pedido2.tempoPedido * (1.0 - taxaDesconto * iteracaoPonderacao)
		);
			
		System.out.println("agSemaforo2: Nova proposta - " + novaProposta);
		
		pedido2.tempoPedido = novaProposta;
		pedido2.tipo = TipoPedido.PEDIR;
	}
}

	

	
		
		
		
		
	
	
		
		
		

	

	
		
		

		

		

		
		
	

	
	
		

		
		

		
	

	

	
	

	
