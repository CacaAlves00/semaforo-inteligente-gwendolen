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

package gwendolen.ail_tutorials.tutorial1;

import ail.mas.DefaultEnvironment;
import ail.syntax.Action;
import ail.syntax.Unifier;
import ail.syntax.NumberTerm;
import ail.syntax.NumberTermImpl;
import ail.syntax.Predicate;
import ail.util.AILexception;

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

	public Integer duracaoTurno = 16;
	private int bufferTempoFluxoLivreSmf1 = duracaoTurno / 2;
	private int bufferTempoFluxoLivreSmf2 = duracaoTurno / 2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see ail.mas.DefaultEnvironment#executeAction(java.lang.String,
	 * ail.syntax.Action)
	 */
	public Unifier executeAction(String agName, Action act) throws AILexception {
		Unifier u = new Unifier();

		/* InterfaceGerenciadora */
		if (act.getFunctor().equals("enviarTempos")) {
			this.bufferTempoFluxoLivreSmf1 = tempoFluxoLivreSmf1;
			this.bufferTempoFluxoLivreSmf2 = tempoFluxoLivreSmf2;
			System.out.println("TESTE: ENTROU P/ O ARTEFATO> " + this.bufferTempoFluxoLivreSmf1 + " "
					+ this.bufferTempoFluxoLivreSmf2);
		}

		if (act.getFunctor().equals("enviarTempos")) {
			// obterDuracaoTurno()
		}

		super.executeAction(agName, act);

		return u;
	}

}
